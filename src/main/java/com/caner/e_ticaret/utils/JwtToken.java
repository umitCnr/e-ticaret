package com.caner.e_ticaret.utils;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtToken {

   /* private String SECRET_KEY = "secretKey";  // Anahtar adını düzelttim
    private long USE_TOKEN_TIME = 604800000L;  // 7 gün

    // Token oluşturma metodu
    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + USE_TOKEN_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // UserDetails objesini alıp token oluşturma metodu
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    // Token'dan claim bilgilerini almak için metod
    Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token).getBody();
    }

    // Claim'den veri çekmek için metod
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Token'ın bitiş zamanını almak için metod
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Token'dan kullanıcı adı almak için metod
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Token'ın geçerliliğini kontrol etmek için metod
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Token geçerliliğini doğrulamak için metod
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }*/
}
