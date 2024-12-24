package com.caner.e_ticaret.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((a) -> a
                .requestMatchers("/api/admin").hasRole("ADMIN")
                .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
                .requestMatchers("/api/seller/**").hasRole("SELLER")
                .anyRequest().authenticated()
        )
                .formLogin(a -> a.permitAll())
                .logout(a -> a.permitAll());


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
