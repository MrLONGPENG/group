package com.lveqia.cloud.common.util;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lveqia.cloud.common.objeck.to.PageTo;

import java.lang.reflect.Type;

public class ResultUtil {

    private final static String VERSION = "20181127";
    private final static String SUCCESS = "Success";

    public final static int CODE_SUCCESS = 200;
    public final static int CODE_TOKEN_INVALID = 201;
    public final static int CODE_PARAMETER_MISS = 202;
    public final static int CODE_REQUEST_FORMAT = 203;
    public final static int CODE_DATA_AUTHORITY = 204;
    public final static int CODE_VALIDATION_FAIL = 205;
    public final static int CODE_DB_STORAGE_FAIL = 206;
    public final static int CODE_REMOTE_CALL_FAIL = 207;
    public final static int CODE_DATA_DUPLICATION = 208;


    public final static int CODE_THIRD_DATA_ERROR  = 210;
    public final static int CODE_UNAUTHORIZED    = 401;
    public final static int CODE_ACCOUNT_DISABLE = 402;
    public final static int CODE_PASSWORD_ERROR  = 403;
    public final static int CODE_NOT_FIND_PATH = 404;
    public final static int CODE_NOT_FIND_DATA = 405;
    
    public final static int CODE_UNKNOWN_ERROR = 500;

    private static Gson gson = new GsonBuilder().setExclusionStrategies().create();

    public static void register(Type type, Object typeAdapter){
        gson = new GsonBuilder().registerTypeAdapter(type,typeAdapter).setExclusionStrategies().create();
    }
    /**
     * 根据CODE返回结果
     */
    public static String code(int code) {
        if(code == 200) return success();
        return error(code);
    }
    /**
     * 根据CODE返回结果
     */
    public static String code(int code , String info) {
        if(code == 200) return success(null, null, info);
        return error(code, info);
    }
    /**
     * 返回正常的结果，不带数据
     */
    public static String success() {
        return success(null, null, SUCCESS);
    }

    /**
     * 返回正常的结果，带数据
     */
    public static String success(Object object) {
        if(object instanceof PageTo){
            PageTo pageTo = (PageTo)object;
            return success(pageTo.getPageList(), pageTo.getPageInfo(), null);
        }
        return success(object, null, null);
    }

    public static String success(Object object, PageInfo pageInfo) {
        return success(object, pageInfo, null);
    }

    public static String success(Object object, PageInfo pageInfo, String info) {
        Result<Object> result = new Result<>();
        result.setCode(CODE_SUCCESS);
        result.setInfo(SUCCESS);
        result.setData(object);
        result.setDesc(info);
        if(pageInfo!=null){
            result.setSize(pageInfo.getSize());
            result.setPages(pageInfo.getPages());
            result.setTotal(pageInfo.getTotal());
            result.setPageNum(pageInfo.getPageNum());
            result.setPageSize(pageInfo.getPageSize());
            result.setLastPage(pageInfo.isIsLastPage());
            result.setFirstPage(pageInfo.isIsFirstPage());
        }
        return gson.toJson(result);

    }

    /**
     * 根据CODE获取提示信息
     */
    public static String getMessage(int code){
        switch (code){
            case CODE_TOKEN_INVALID:    return "登陆凭证无效";
            case CODE_PARAMETER_MISS:   return "必要参数缺失";
            case CODE_REQUEST_FORMAT:   return "请求参数格式错误";
            case CODE_DATA_AUTHORITY:   return "无此数据权限";
            case CODE_VALIDATION_FAIL:  return "效验数据失败";
            case CODE_DB_STORAGE_FAIL:  return "数据存储失败";
            case CODE_REMOTE_CALL_FAIL: return "远程调用服务失败";
            case CODE_THIRD_DATA_ERROR: return "第三方数据格式错误";
            case CODE_DATA_DUPLICATION: return "已存在重复的数据";
            case CODE_UNAUTHORIZED   : return "账户无权限, 无法操作，请联系管理员!";
            case CODE_ACCOUNT_DISABLE: return "账户被禁用，登录失败，请联系管理员!";
            case CODE_PASSWORD_ERROR:  return "用户名或密码输入错误，登录失败!";
            case CODE_NOT_FIND_DATA:   return "无法找到数据，请检查请求条件！";
            case CODE_NOT_FIND_PATH:   return "接口路径没有找到，请检查路由!";
            case CODE_UNKNOWN_ERROR:   return "未知错误";
        }
        return "Unknown error";
    }

    /**
     * 返回错误的结果，带默认错误提示
     */
    public static String error(int code) {
        return error(code,getMessage(code));
    }

    /**
     * 返回错误的结果，带错误提示
     */
    public static String error(int code, String info) {
        if(info == null) return error(code);
        Result<String> result = new Result<>();
        result.setCode(code);
        result.setInfo(info);
        return gson.toJson(result);
    }

    private static class Result<T>{
        private int code;
        private long timestamp;
        private String info;
        private String desc;
        private String version;
        private T data;

        private Integer pageNum;        //当前页
        private Integer pageSize;       //每页的数量
        private Integer size;           //当前页的数量
        private Integer pages;          //总页数
        private Long total;             //总数量
        private Boolean isFirstPage;    //是否为第一页
        private Boolean isLastPage;     //是否为最后一页


        public Result() {
            this.version = VERSION;
            this.timestamp = System.currentTimeMillis()/1000;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Integer getPageNum() {
            return pageNum;
        }

        private void setPageNum(Integer pageNum) {
            this.pageNum = pageNum;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        private void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getSize() {
            return size;
        }

        private void setSize(Integer size) {
            this.size = size;
        }

        public Integer getPages() {
            return pages;
        }

        private void setPages(Integer pages) {
            this.pages = pages;
        }

        public Long getTotal() {
            return total;
        }

        private void setTotal(Long total) {
            this.total = total;
        }

        public Boolean getFirstPage() {
            return isFirstPage;
        }

        private void setFirstPage(Boolean firstPage) {
            isFirstPage = firstPage;
        }

        public Boolean getLastPage() {
            return isLastPage;
        }

        private void setLastPage(Boolean lastPage) {
            isLastPage = lastPage;
        }
    }
}
