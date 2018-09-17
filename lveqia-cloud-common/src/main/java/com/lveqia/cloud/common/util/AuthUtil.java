package com.lveqia.cloud.common.util;

import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.objeck.info.UserInfo;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AuthUtil implements Serializable {

    private final static String TOKEN_HEAD = "Bearer";
    private final static String TOKEN_HEADER = "Authorization";
    private static final String CLAIM_KEY_UID = "uid" ;
    private static byte[] secret = "cloud_auth".getBytes();
    //private static Logger logger = LoggerFactory.getLogger(AuthUtil.class);

    public static long getUidByRequest(HttpServletRequest request) {
        return getUidFromToken(getToken(request));
    }


    public static String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(TOKEN_HEADER);
        if (authHeader != null && authHeader.startsWith(TOKEN_HEAD)) {
            return authHeader.substring(TOKEN_HEAD.length()); // The part after "Bearer "
        }
        return null;
    }

    public static String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }



    private static long getUidFromToken(String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            return  claims.get(CLAIM_KEY_UID, Long.class);
        } catch (Exception e) {
            System.out.println("token");
        }
        return 0L;
    }


    private static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生成过期时间
     */
    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + Constant.TIMESTAMP_HOUR_3 * 1000);
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public static String generateToken(UserInfo userInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_UID, userInfo.getId());
        return Jwts.builder().setClaims(claims).setSubject(userInfo.getUsername())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static boolean validateToken(String authToken, UserInfo userInfo) {
        final String username = getUsernameFromToken(authToken);
        return username.equals(userInfo.getUsername()) && !isTokenExpired(authToken);
    }
}
