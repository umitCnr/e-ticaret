package com.caner.e_ticaret.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    /**
     * OncePerRequestFilter sayesinde http request ve responslarında cevap vermek için kullanılır.
     * http cevabı oluşturulacak token doğruluğu ve ilgili http isteğine gitmesini sağlıcaz
     **/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Bearer ifadesi ile token ın gelmesi gerekiyor
        if (header == null || header.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        jwt = header.substring(7);
        username = jwtService.findUsername(jwt); // username i JwtService den alıyoruz.

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            /** var olan username değişkeni
             * null mı onu kontrol ediyoruz eğer null değilse userdetail
             * sınıfının username metodu çağırıcaz ve springe aktarmış olacaz
             **/
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.tokenControl(jwt, userDetails)) {

                // UsernamePasswordAuthenticationToken: bu nesne userdetails parametresi alarak kullanıcı bilgilerini oluşturacak ve yetkilerini buraya verecez
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }


}


