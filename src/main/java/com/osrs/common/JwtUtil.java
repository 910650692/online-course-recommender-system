package com.osrs.common;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtUtil {
    private static final String SECRET_KEY = "23332898";
    private static Set<String> blacklist = new HashSet<String>();
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours token validity
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    public static String verifyToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }
    public static boolean invalidateToken(String token) {
        // 检查token是否已经失效
        if (blacklist.contains(token)) {
            return false;
        }

        // 将token添加到黑名单中
        blacklist.add(token);
        return true;
    }

}