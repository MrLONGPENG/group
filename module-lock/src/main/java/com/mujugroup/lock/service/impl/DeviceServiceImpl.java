package com.mujugroup.lock.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.common.StringUtil;
import com.mujugroup.lock.model.LockDid;
import com.mujugroup.lock.service.AuthService;
import com.mujugroup.lock.service.DeviceService;
import com.mujugroup.lock.service.LockDidService;
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

    @Autowired
    public DeviceServiceImpl(RestTemplate restTemplate, AuthService authService, LockDidService lockDidService) {
        this.restTemplate = restTemplate;
        this.authService = authService;
        this.lockDidService = lockDidService;
    }

    @Override
    public JsonObject unlock(String did) {
        return httpMethod(methodUnlock, HttpMethod.POST, didToBid(did)); // 若不是锁ID 转换
    }

    @Override
    public JsonObject query(String did) {
        return httpMethod(methodQuery, HttpMethod.GET, didToBid(did)); // 若不是锁ID 转换
    }

    @Override
    public JsonObject beep(String did) {
        return httpMethod(methodBeep, HttpMethod.POST, didToBid(did)); // 若不是锁ID 转换
    }

    @Override
    public JsonObject ble(String did) {
        return httpMethod(methodBle, HttpMethod.GET, didToBid(did)); // 若不是锁ID 转换
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
        if(did.length() < 9 ) did = StringUtil.autoFillDid(did);
        LockDid lockDid = lockDidService.getLockDidByDid(did);
        if(lockDid != null) return StringUtil.autoFillDid(lockDid.getLockId(),19);
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
