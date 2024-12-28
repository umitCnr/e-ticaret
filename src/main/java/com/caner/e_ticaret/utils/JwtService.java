package com.caner.e_ticaret.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

/* TODO  token içerisinde parse edip kullanılması için ya da token generate edilmesi için bir service sınıfı oluşturuyoruz */

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String SECRET_KEY;

    //Buraya gelen token Claims de subject bölümünden username i çekilmesini sağlıyor
    public String findUsername(String token) {

        return exportToken(token, Claims::getSubject);
    }

    private <T> T exportToken(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey()) //oluşturulan tokenın secret key kısmını oluşturuyoruz
                .build().parseClaimsJws(token).getBody(); // burda token ı parse ettik
        return claimsTFunction.apply(claims); //böylece findUsername burda oluşturulan subject name'i almış olacaz
    }

    private Key getKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }


    public boolean tokenControl(String jwt, UserDetails userDetails) { //burda kontrol yapıyoruz jwt ve username geçerlimi onu kontrol ediyoruz
        final String username = findUsername(jwt);
        return (username.equals(userDetails.getUsername())) && !exportToken(jwt, Claims::getExpiration).before(new Date()); // burada token süresi kontrolü yapıyoruz
        //tokne süresi dolduysa true , eğer dolmamış ise false dönecek
    }

    public String generateToken(UserDetails user) { // userdetail içersinde bir token oluşturma
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
