package com.lveqia.cloud.zuul.config;

import com.lveqia.cloud.zuul.model.SysMenu;
import com.lveqia.cloud.zuul.model.SysRole;
import com.lveqia.cloud.zuul.service.SysMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final SysMenuService sysMenuService;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    private static Logger logger = LoggerFactory.getLogger(UrlFilterInvocationSecurityMetadataSource.class);
    @Autowired
    public UrlFilterInvocationSecurityMetadataSource(SysMenuService sysMenuService) {
        logger.debug("UrlFilterInvocationSecurityMetadataSource");
        this.sysMenuService = sysMenuService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();        //获取请求地址
        logger.debug("url filter ->{}", requestUrl);
        List<SysMenu> allMenu = sysMenuService.getAllMenuByLength();
        for (SysMenu menu : allMenu) {
            if (antPathMatcher.match(menu.getUrl(), requestUrl) && menu.getRoles().size()>0) {
                logger.debug("url:{} requestUrl:{}", menu.getUrl(), requestUrl);
                List<SysRole> roles = menu.getRoles();
                int size = roles.size();
                String[] values = new String[size];
                for (int i = 0; i < size; i++) {
                    values[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(values);
            }
        }

        return null; //没有匹配上的资源，不做权限控制
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        logger.debug("UrlFilterInvocationSecurityMetadataSource->getAllConfigAttributes");
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        logger.debug("UrlFilterInvocationSecurityMetadataSource->supports");
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
