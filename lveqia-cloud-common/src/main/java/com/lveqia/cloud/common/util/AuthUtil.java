package com.lveqia.cloud.common.util;

import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.objeck.info.UserInfo;
import io.jsonwebtoken.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;


public class AuthUtil implements Serializable {

    private final static String TOKEN_HEAD = "Bearer "; //注意空隔
    private final static String TOKEN_HEADER = "Authorization";
    private static final String CLAIM_KEY_UID = "uid" ;
    private static final String CLAIM_KEY_URG = "urg" ;
    private static byte[] secret = "cloud_auth".getBytes();
    private static final long EXPIRATION_TOKEN = Constant.TIMESTAMP_DAYS_1 * 1000;
    public static final long EXPIRATION_REDIS = Constant.TIMESTAMP_HOUR_3 * 1000;

    //private static Logger logger = LoggerFactory.getLogger(AuthUtil.class);


    private static String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(TOKEN_HEADER);
        if (authHeader != null && authHeader.startsWith(TOKEN_HEAD)) {
            return authHeader.substring(TOKEN_HEAD.length()); // The part after "Bearer "
        }
        return null;
    }

    /**
     * 根据jwt token 获取用户信息
     */
    public static UserInfo getUserInfo(HttpServletRequest request){
        return getUserInfo(getToken(request));
    }

    /**
     * 获取Redis中的Key; 后面增加token:app:xxx格式
     */
    public static String getKey(UserInfo userInfo) {
        return Optional.ofNullable(userInfo).map(info-> "token:vue:"+info.getId()).orElse("token:vue:0");
    }

    private static UserInfo getUserInfo(String token){
        if(StringUtil.isEmpty(token)) return null;
        final Claims claims = getClaimsFromToken(token);
        if(claims != null){
            UserInfo userInfo = new UserInfo(claims.getSubject());
            userInfo.setId(claims.get(CLAIM_KEY_UID, Long.class));
            userInfo.setRoleInfo(claims.get(CLAIM_KEY_URG, List.class));
            userInfo.setExpiration(claims.getExpiration());
            userInfo.setName(claims.getAudience());
            userInfo.setToken(token);
            return userInfo;
        }
        return null;
    }

    /**
     * 判断用户信息是否过期
     */
    public static Boolean isTokenExpired(UserInfo userInfo) {
        return Optional.ofNullable(userInfo).map(UserInfo::getExpiration)
                .map(date -> date.before(new Date())).orElse(true);
    }

    /**
     * Token字符串转Claims
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ClaimJwtException e) {
            claims = e.getClaims();
        }
        return claims;
    }



    public static String generateToken(UserInfo userInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_UID, userInfo.getId());
        claims.put(CLAIM_KEY_URG, userInfo.getRoleInfo());
        return Jwts.builder().setClaims(claims).setSubject(userInfo.getUsername()).setAudience(userInfo.getName())
                .setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, secret).compact();
    }


    /**
     * 生成过期时间
     */
    private static Date generateExpirationDate() {
        return generateExpirationDate(EXPIRATION_TOKEN);
    }
    /**
     * 生成过期时间
     */
    public static Date generateExpirationDate(long time) {
        return new Date(System.currentTimeMillis() + time);
    }


}
