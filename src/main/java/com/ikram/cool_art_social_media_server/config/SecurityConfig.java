package com.ikram.cool_art_social_media_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean // set Password Encode that will be used in User Service
    public PasswordEncoder passwordEncoder() {
        // Use cost value 12 to meet OWASP standard
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // to turn off CSRF because this is REST API
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // allow all request to pass through
                );
        return http.build();
    }
}