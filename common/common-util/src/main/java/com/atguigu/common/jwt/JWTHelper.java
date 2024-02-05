package com.atguigu.common.jwt;

import io.jsonwebtoken.*;
import javafx.beans.binding.Bindings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
@Slf4j
public class JWTHelper {
    //30分钟过期
    private static long tokenExpiration = 60 * 356;
    private static String tokenSignKey = "kele1234kele1234kele1234kele1234kele1234kele1234kele1234kele1234kele1234kele1234kele1234kele1234kele1234kele1234kele1234kele1234kele1234kele1234";
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static String creatToken(Long userID, String username) {
        String token = Jwts.builder()
                .setSubject("auth-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration * 1000))
                .claim("userId", userID)
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();

        return token;
    }

    public static Long getUserId(String token) {
        if (StringUtils.isEmpty(token))
            return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId.longValue();
    }

    public static String getUsername(String token) {
        Claims claims1 = Jwts.parser()
                .setSigningKey(tokenSignKey)
                .parseClaimsJws(token)
                .getBody();
        Date d1 = claims1.getIssuedAt();
        Date d2 = claims1.getExpiration();
        System.out.println("username参数值：" + claims1.get("username"));
        System.out.println("登录用户的id：" + claims1.getId());
        System.out.println("登录用户的名称：" + claims1.getSubject());
        System.out.println("令牌签发时间：" + sdf.format(d1));
        System.out.println("令牌过期时间：" + sdf.format(d2));

        try {
            if (StringUtils.isEmpty(token)) return "";
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();

            return (String) claims.get("username");
        } catch (ExpiredJwtException e) {
            log.error("token{}过期", token, e);
            throw new RuntimeException("token{}过期");
        } catch (SignatureException e) {
            log.error("token=[{}], 签名", token, e);
            throw new RuntimeException("token{}签名错误");
        } catch (Exception e) {
            log.error("token=[{}]解析错误 message:{}", token, e.getMessage(), e);
            throw new RuntimeException("token{}解析错误");
        }
    }
}