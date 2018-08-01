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
                .refreshAfterWrite(mergeProperties.getGuavaCacheRefreshWriteTime(), TimeUnit.MINUTES)
                .build(new CacheLoader<String, Map<String, String>>() {
                    @Override
                    public Map<String, String> load(String key) throws Exception {
                        logger.debug("首次读取缓存: " + key);
                        MergeField mergeField = mergeFieldMap.get(key);
                        Object bean = BeanFactoryUtils.getBean(mergeField.feign());
                        Method method = mergeField.feign().getMethod(mergeField.method(), String.class);
                        String query = key.contains("#")? key.substring(key.indexOf(",")+1) : mergeField.key();
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
                            String query = key.contains("#")? key.substring(key.indexOf(",")+1) : mergeField.key();
                            Map<String, String> invoke = (Map<String, String>) method.invoke(bean,query);
                            return invoke;
                        });
                    }
                });

    }


    /**
     * aop方式加工
     *
     * @param pjp
     * @param anno
     * @return
     * @throws Throwable
     */
    public Object mergeData(ProceedingJoinPoint pjp, MergeResult anno) throws Throwable {
        Object proceed = pjp.proceed();
        try {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method m = signature.getMethod();
            Type returnType = m.getGenericReturnType();
            if(!(returnType instanceof ParameterizedType)){
                mergeOne(proceed.getClass(), proceed);
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
     *
     * @param clazz
     * @param result
     * @param params
     * @throws ExecutionException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void mergeResult(Class clazz, List<?> result, Object[] params) throws NoSuchMethodException
            , InvocationTargetException, IllegalAccessException, ExecutionException {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> mergeFields = new ArrayList<Field>();
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
                                    sb.append(o.toString()).append(",");
                                }
                            }
                        } catch (IllegalAccessException e) {
                            logger.error("数据属性加工失败:" + field, e);
                            throw new RuntimeException("数据属性加工失败:" + field, e);
                        }

                    });
                    args = sb.substring(0, sb.length() - 1);
                } else {
                    String key = className + field.getName();
                    if(annotation.isQueryByParam()){
                        StringBuffer sb = new StringBuffer(className);
                        sb.append("#").append(field.getName());
                        for (Object object:params) {
                            sb.append(",").append(object);
                        }
                        key = new String(sb);
                    }
                    mergeFieldMap.put(key, annotation);
                    // 从缓存获取
                    Map<String, String> value = caches.get(key);
                    if (value != null) {
                        if(value.size()==0) caches.refresh(key);
                        invokes.put(field.getName(), addDefaultKey(value, annotation.value()));
                        continue;
                    }
                }
                Object bean = BeanFactoryUtils.getBean(annotation.feign());
                Method method = annotation.feign().getMethod(annotation.method(), String.class);
                Map<String, String> value = (Map<String, String>) method.invoke(bean, args);
                invokes.put(field.getName(), addDefaultKey(value, annotation.value()));
            }
        }
        result.forEach(obj -> mergeObjFieldValue(obj, mergeFields, invokes));
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
     * 手动对单个结果进行配置合并
     *
     * @param clazz
     * @param mergeObj
     * @throws ExecutionException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void mergeOne(Class clazz, Object mergeObj) throws ExecutionException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> mergeFields = new ArrayList<Field>();
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
                } else {
                    String key = className + field.getName();
                    mergeFieldMap.put(key, annotation);
                    // 从缓存获取
                    Map<String, String> value = caches.get(key);
                    if (value != null) {
                        if(value.size()==0) caches.refresh(key);
                        invokes.put(field.getName(), addDefaultKey(value, annotation.value()));
                        continue;
                    }
                }
                Object bean = BeanFactoryUtils.getBean(annotation.feign());
                Method method = annotation.feign().getMethod(annotation.method(), String.class);
                Map<String, String> value = (Map<String, String>) method.invoke(bean, args);
                invokes.put(field.getName(), addDefaultKey(value, annotation.value()));
            }
        }
        mergeObjFieldValue(mergeObj, mergeFields, invokes);
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
