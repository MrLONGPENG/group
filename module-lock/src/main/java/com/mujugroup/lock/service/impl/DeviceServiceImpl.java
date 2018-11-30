package com.mujugroup.lock.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.lock.model.LockDid;
import com.mujugroup.lock.service.AuthService;
import com.mujugroup.lock.service.DeviceService;
import com.mujugroup.lock.service.LockDidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {
    @Value("${spring.profiles.active}")
    private String model;

    @Value("${base_url}")
    String baseUrl;

    @Value("${method_common}")
    String methodCommon;

    @Value("${method_unlock}")
    String methodUnlock;

    @Value("${method_query}")
    String methodQuery;

    @Value("${method_beep}")
    String methodBeep;

    @Value("${method_ble}")
    String methodBle;

    private final RestTemplate restTemplate;
    private final AuthService authService;
    private final LockDidService lockDidService;

    private final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    public DeviceServiceImpl(RestTemplate restTemplate, AuthService authService, LockDidService lockDidService) {
        this.restTemplate = restTemplate;
        this.authService = authService;
        this.lockDidService = lockDidService;
    }

    private JsonObject unlock(String did) {
        return httpMethod(methodUnlock, HttpMethod.POST, didToBid(did)); // 若不是锁ID 转换
    }

    private JsonObject query(String did) {
        return httpMethod(methodQuery, HttpMethod.GET, didToBid(did)); // 若不是锁ID 转换
    }

    private JsonObject beep(String did) {
        return httpMethod(methodBeep, HttpMethod.POST, didToBid(did)); // 若不是锁ID 转换
    }

    private JsonObject ble(String did) {
        return httpMethod(methodBle, HttpMethod.GET, didToBid(did)); // 若不是锁ID 转换
    }

    /**
     * 远程调用连旅接口
     * @param type 0：开锁 1：查询 2：寻车铃 3：蓝牙信息
     * @param did  业务ID(DID) 或 锁设备ID(BID)
     */
    @Override
    public String remoteCall(int type, String did) {
        logger.debug("remoteCall:"+did);
        if(did == null) return ResultUtil.error(ResultUtil.CODE_PARAMETER_MISS);
        if(!StringUtil.isNumeric(did)) return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT);
        if(did.length()>9 && did.length()!=19) return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT);
        if(Constant.MODEL_DEV.equals(model)) {
            logger.info("远程调用锁接口，Did: {}, type: {}", did, type);
            return ResultUtil.success("{\"code\":200}");
        }
        JsonObject object = null;
        switch (type){
            case 0: object = unlock(did);break;
            case 1: object = query(did);break;
            case 2: object = beep(did);break;
            case 3: object = ble(did);break;
        }
        if(object==null ) return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        if(object.has("code")) return ResultUtil.code(object.get("code").getAsInt());
        return ResultUtil.success(object);
    }
    /**
     * 远程调用连旅接口，获取信息, 根据结果返回数据
     * @param method  接口名
     * @param httpMethod Http类型 Get Post
     */
    private JsonObject httpMethod(String method, HttpMethod httpMethod, String did) {
        JsonObject json = getResponse(baseUrl + method, httpMethod,false, did);
        if(json.has("code") && json.get("code").getAsInt() == ResultUtil.CODE_TOKEN_INVALID){
            json = getResponse(baseUrl + method, httpMethod,true, did);
        }
        if(json.has("code") && json.get("code").getAsInt() == ResultUtil.CODE_SUCCESS){
            if(json.has("result")){
                return json.getAsJsonObject("result");
            }else{
                return json;
            }
        }else if(json.has("code")){
            return json;
        }
        return  null;
    }

    /**
     * 效验DID位数，当不是BID时候，装换成BID开锁
     * @param did 业务ID或锁ID
     */
    @Override
    public String didToBid(String did) {
        if(did.length() == 19) return did;
        if(did.length() < 9 ) did = StringUtil.autoFillDid(did);
        LockDid lockDid = lockDidService.getLockDidByDid(did);
        if(lockDid != null) return lockDid.getLockId();
        return null;

    }

    /**
     * 远程调用，转换成Json对象
     * @param url 地址
     * @param method  get or post
     * @param isRefresh 是否刷新token
     */
    private JsonObject getResponse(String url,HttpMethod method, boolean isRefresh, String did) {
        JsonObject object = new JsonObject();
        try{
            if(did == null) {
                object.addProperty("code", ResultUtil.CODE_NOT_FIND_DATA);
                return object;
            }
            ResponseEntity<String>  response  = restTemplate.exchange(url, method
                    , new HttpEntity<>(getHeader(isRefresh)), String.class, did);
            if(response.getStatusCodeValue() == 500){
                object.addProperty("code", ResultUtil.CODE_NOT_FIND_DATA);
                return object;
            }
            String result = response.getBody();
            try {
                if(result!=null){
                    return new JsonParser().parse(result).getAsJsonObject();
                }
            }catch (JsonSyntaxException e){
                object.addProperty("code", ResultUtil.CODE_THIRD_DATA_ERROR);
            }
        }catch (Exception e){
            object.addProperty("code", ResultUtil.CODE_NOT_FIND_DATA);
        }
        return object;
    }


    /**
     * 获取通用的Header头信息
     * @return header info
     */
   private MultiValueMap<String, String> getHeader(boolean isRefresh){
       MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
       header.add("channel","com_aihui");
       header.add("authorization",getToken(isRefresh));
       return header;
    }


    /**
     * 获取Token信息
     * @return header info
     */
    private String getToken(boolean isRefresh){
        String token = "Unknown";
        String  result = isRefresh? authService.putToken() : authService.getToken();
        try {
            JsonObject json = new JsonParser().parse(result).getAsJsonObject();
            if(json.has("code") && json.get("code").getAsInt() ==200){
                token = json.getAsJsonObject("result").get("access_token").getAsString();
            }
        }catch (Exception e){
            System.out.println("解析token失败");
        }
        return token;
    }
}
