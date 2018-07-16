package com.mujugroup.lock.service.impl;

import com.mujugroup.lock.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Value("${base_url}")
    String baseUrl;

    @Value("${method_token}")
    String methodToken;

    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    public AuthServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @CachePut(value = "lock_auth_token", unless="#result == null")
    public String putToken() {
        logger.debug("lock-AuthServiceImpl-putToken");
        return restTemplate.postForObject(baseUrl+methodToken, getTokenEntity(), String.class);
    }

    @Cacheable(value = "lock_auth_token", unless="#result == null")
    public String getToken() {
        logger.debug("lock-AuthServiceImpl-getToken");
        return restTemplate.postForObject(baseUrl+methodToken, getTokenEntity(), String.class);
    }

    /**
     * 拼凑登录表单数据
     * @return 登录表单实体
     */
    public HttpEntity<?> getTokenEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("username", "admin");
        params.add("password", "admin");
        params.add("channel", "com_aihui");
        return new HttpEntity<>(params, headers);
    }
}
