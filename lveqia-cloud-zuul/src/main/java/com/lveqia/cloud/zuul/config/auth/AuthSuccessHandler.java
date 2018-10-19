package com.lveqia.cloud.zuul.config.auth;

import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.AuthUtil;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.zuul.config.RedisCacheManager;
import com.lveqia.cloud.zuul.model.SysRole;
import com.lveqia.cloud.zuul.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    private final RedisCacheManager redisCacheManager;
    private final Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);

    @Autowired
    public AuthSuccessHandler(RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        logger.debug("getPrincipal {}", authentication.getPrincipal());
        UserInfo userInfo = getUserInfo((SysUser) authentication.getPrincipal());
        if(authentication instanceof AppToken){ // 设置登陆来源，app与vue
            userInfo.setTag(AuthUtil.AUTH_TAG_APP);
        }else{
            userInfo.setTag(AuthUtil.AUTH_TAG_VUE);
        }
        PrintWriter out = response.getWriter();
        String key = AuthUtil.getKey(userInfo);
        String token =  redisCacheManager.getToken(key);
        if(StringUtil.isEmpty(token)) { // 如果Redis不存在Token则创建新的，同时
            token = AuthUtil.generateToken(userInfo);
            redisCacheManager.setToken(key , token);
        }
        redisCacheManager.expireAt(key, AuthUtil.generateExpirationDate(AuthUtil.EXPIRATION_REDIS));
        userInfo.setToken(token);
        out.write(ResultUtil.success(userInfo));
        out.flush();
        out.close();
    }

    /**
     * 根据SysUser构建通用的UserInfo
     */
    private UserInfo getUserInfo(SysUser user) {
        UserInfo userInfo = new UserInfo(user.getId(), user.getName(), user.getUsername());
        List<String> authorities = new ArrayList<>();
        for (SysRole role : user.getRoles()) {
            authorities.add(role.getName().substring(5));
        }
        userInfo.setRoleInfo(authorities);
        return userInfo;
    }
}
