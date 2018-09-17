package com.github.wxiaoqi.merge.core;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.github.wxiaoqi.merge.configuration.MergeProperties;
import com.github.wxiaoqi.merge.facade.DefaultMergeResultParser;
import com.github.wxiaoqi.merge.facade.IMergeResultParser;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.lveqia.cloud.common.config.Constant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ace
 * @create 2018/2/2.
 */
@Slf4j
public class MergeCore {

    private static final String DEFAULT_KEY = "default_key";
    private Map<String, MergeField> mergeFieldMap;
    private ListeningExecutorService backgroundRefreshPools;
    private LoadingCache<String, Map<String, String>> caches;
    private final Logger logger = LoggerFactory.getLogger(MergeCore.class);
    public MergeCore(MergeProperties mergeProperties) {
        this.backgroundRefreshPools =
                MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(mergeProperties.getGuavaCacheRefreshThreadPoolSize()));
        this.mergeFieldMap = new HashMap<>();
        this.caches = CacheBuilder.newBuilder()
                .maximumSize(mergeProperties.getGuavaCacheNumMaxSize())
                .expireAfterWrite(mergeProperties.getGuavaCacheExpireWriteTime(), TimeUnit.MINUTES)
                .refreshAfterWrite(mergeProperties.getGuavaCacheRefreshWriteTime(), TimeUnit.MINUTES)
                .build(new CacheLoader<String, Map<String, String>>() {
                    @Override
                    public Map<String, String> load(String key) throws Exception {
                        logger.debug("首次读取缓存: " + key);
                        MergeField mergeField = mergeFieldMap.get(key);
                        Object bean = BeanFactoryUtils.getBean(mergeField.feign());
                        Method method = mergeField.feign().getMethod(mergeField.method(), String.class);
                        String query = key.contains(Constant.SIGN_NUMBER)
                                ? key.substring(key.indexOf(Constant.SIGN_COMMA)+1) : mergeField.key();
                        Map<String, String> invoke = (Map<String, String>) method.invoke(bean,query);
                        return invoke;
                    }

                    @Override
                    public ListenableFuture<Map<String, String>> reload(final String key, Map<String, String> oldValue){
                        return backgroundRefreshPools.submit(() -> {
                            logger.debug("异步刷新缓存: " + key);
                            MergeField mergeField = mergeFieldMap.get(key);
                            Object bean = BeanFactoryUtils.getBean(mergeField.feign());
                            Method method = mergeField.feign().getMethod(mergeField.method(), String.class);
                            String query = key.contains(Constant.SIGN_NUMBER)
                                    ? key.substring(key.indexOf(Constant.SIGN_COMMA)+1) : mergeField.key();
                            Map<String, String> invoke = (Map<String, String>) method.invoke(bean,query);
                            return invoke;
                        });
                    }
                });

    }


    /**
     * aop方式加工
     */
    public Object mergeData(ProceedingJoinPoint pjp, MergeResult anno) throws Throwable {
        Object proceed = pjp.proceed();
        try {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method m = signature.getMethod();
            Type returnType = m.getGenericReturnType();
            if(!(returnType instanceof ParameterizedType)){
                mergeOne(proceed.getClass(), proceed, pjp.getArgs());
                return proceed;
            }
            ParameterizedType parameterizedType = (ParameterizedType)returnType;
            Type rawType = parameterizedType.getRawType();
            List<?> result;
            // 获取当前方法的返回值
            Type[] types = parameterizedType.getActualTypeArguments();
            Class clazz = ((Class) types[0]);
            // 非list直接返回
            if (anno.resultParser().equals(DefaultMergeResultParser.class)
                    && ((Class) rawType).isAssignableFrom(List.class)) {
                result = (List<?>) proceed;
                mergeResult(clazz, result, pjp.getArgs());
                return result;
            } else {
                IMergeResultParser bean = BeanFactoryUtils.getBean(anno.resultParser());
                result = bean.parser(proceed);
                mergeResult(clazz, result, pjp.getArgs());
                return proceed;
            }
        } catch (Exception e) {
            logger.error("某属性数据聚合失败", e);
            return proceed;
        }

    }

    /**
     * 手动调用进行配置合并
     */
    public void mergeResult(Class clazz, List<?> result, Object[] params) throws NoSuchMethodException
            , InvocationTargetException, IllegalAccessException, ExecutionException {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> mergeFields = new ArrayList<>();
        Map<String, Map<String, String>> invokes = new HashMap<>();
        String className = clazz.getName();
        // 获取属性
        for (Field field : fields) {
            MergeField annotation = field.getAnnotation(MergeField.class);
            if (annotation != null) {
                mergeFields.add(field);
                String args = annotation.key();
                // 表示该属性需要将值聚合成条件远程查询
                if (annotation.isValueNeedMerge()) {
                    args = getKey(args, result, field);
                } else {
                    Map<String, String> value = getValueByKey(params, className, field, annotation);
                    if (value != null) {
                        invokes.put(field.getName(), addDefaultKey(value, annotation.defaultValue()));
                        continue;
                    }
                }
                Object bean = BeanFactoryUtils.getBean(annotation.feign());
                Method method = annotation.feign().getMethod(annotation.method(), String.class);
                Map<String, String> value = (Map<String, String>) method.invoke(bean, args);
                invokes.put(field.getName(), addDefaultKey(value, annotation.defaultValue()));
            }
        }
        result.forEach(obj -> mergeObjFieldValue(obj, mergeFields, invokes));
    }





    /**
     * 手动对单个结果进行配置合并
     */
    public void mergeOne(Class clazz, Object mergeObj, Object[] params) throws ExecutionException
            , NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> mergeFields = new ArrayList<>();
        Map<String, Map<String, String>> invokes = new HashMap<>();
        String className = clazz.getName();
        // 获取属性
        for (Field field : fields) {
            MergeField annotation = field.getAnnotation(MergeField.class);
            if (annotation != null) {
                mergeFields.add(field);
                String args = annotation.key();
                // 表示该属性需要将值聚合成条件远程查询
                if (annotation.isValueNeedMerge()) {
                    args = getKeyByOne(args, mergeObj, field);
                } else {
                    Map<String, String> value = getValueByKey(params, className, field, annotation);
                    if (value != null) {
                        invokes.put(field.getName(), addDefaultKey(value, annotation.defaultValue()));
                        continue;
                    }
                }
                Object bean = BeanFactoryUtils.getBean(annotation.feign());
                Method method = annotation.feign().getMethod(annotation.method(), String.class);
                Map<String, String> value = (Map<String, String>) method.invoke(bean, args);
                invokes.put(field.getName(), addDefaultKey(value, annotation.defaultValue()));
            }
        }
        mergeObjFieldValue(mergeObj, mergeFields, invokes);
    }


    /**
     * 根据属性合并查询Key
     */
    private String getKey(String args, List<?> result, Field field) {
        StringBuffer sb = new StringBuffer();
        Set<String> ids = new HashSet<>();
        result.forEach(obj -> {
            field.setAccessible(true);
            Object o;
            try {
                o = field.get(obj);
                if (o != null) {
                    if (!ids.contains(o)) {
                        ids.add(o.toString());
                        sb.append(o.toString()).append(Constant.SIGN_SEMICOLON);
                    }
                }
            } catch (IllegalAccessException e) {
                logger.error("数据属性加工失败:" + field, e);
                throw new RuntimeException("数据属性加工失败:" + field, e);
            }

        });
        if(sb.length() > 0) args = sb.substring(0, sb.length() - 1);
        return args;
    }


    /**
     * 单个属性 不需要循环组合查询条件
     */
    private String getKeyByOne(String args, Object mergeObj, Field field) {
        field.setAccessible(true);
        Object o;
        try {
            o = field.get(mergeObj);
        } catch (IllegalAccessException e) {
            logger.error("数据属性加工失败:" + field, e);
            throw new RuntimeException("数据属性加工失败:" + field, e);
        }
        if (o != null) {
            args = o.toString();
        }
        return args;
    }

    /**
     * 根据方法的参数，获取远程数据结果集，同时判定是否需要缓存
     */
    private Map<String, String> getValueByKey(Object[] params, String className, Field field, MergeField annotation)
            throws ExecutionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String key = className + field.getName();
        if(annotation.isQueryByParam()){// 采用格式：类名#属性名,参数1,参数n
            StringBuffer sb = new StringBuffer(className);
            sb.append(Constant.SIGN_NUMBER).append(field.getName());
            for (Object object:params) {
                sb.append(Constant.SIGN_COMMA).append(object);
            }
            key = new String(sb);
        }
        Map<String, String> value;
        if(annotation.isCache()) { // 从缓存获取
            mergeFieldMap.put(key, annotation);
            value = caches.get(key);
        }else {
            Object bean = BeanFactoryUtils.getBean(annotation.feign());
            Method method = annotation.feign().getMethod(annotation.method(), String.class);
            String query = key.contains(Constant.SIGN_NUMBER) ? key.substring(
                    key.indexOf(Constant.SIGN_COMMA) + 1 ) : annotation.key();
            value = (Map<String, String>) method.invoke(bean,query);
        }
        return value;
    }

    /**
     * 判定是否存储默认值，存在则添加
     * @param map
     * @param value
     */
    private Map<String,String> addDefaultKey(Map<String,String> map, String value) {
        if(!"".equals(value)) map.put(DEFAULT_KEY, value);
        return map;
    }



    /**
     * 合并对象属性值
     * @param mergeObj
     * @param mergeFields
     * @param invokes
     */
    private void mergeObjFieldValue(Object mergeObj, List<Field> mergeFields, Map<String, Map<String, String>> invokes) {
        Object o;
        Map<String, String> m;
        for (Field field : mergeFields) {
            field.setAccessible(true);
            try {
                o = field.get(mergeObj);
                m = invokes.get(field.getName());
                if (o != null && m.containsKey(String.valueOf(o))) {
                    field.set(mergeObj, m.get(o.toString()));
                } else if(m.containsKey(DEFAULT_KEY)){
                    field.set(mergeObj, m.get(DEFAULT_KEY));
                }
            } catch (IllegalAccessException e) {
                logger.error("数据属性加工失败:" + field, e);
                throw new RuntimeException("数据属性加工失败:" + field, e);
            }
        }
    }
}
