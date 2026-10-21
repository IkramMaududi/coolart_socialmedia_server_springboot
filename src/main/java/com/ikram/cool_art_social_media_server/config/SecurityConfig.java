package com.ikram.cool_art_social_media_server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean // set Password Encode that will be used in User Service
    public PasswordEncoder passwordEncoder() {
        // Use cost value 12 to meet OWASP standard
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // disable CSRF & enable CORS
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults()) // activate CORS
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // set session management to stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // routes than can be accessed without token
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login", "/user/register").permitAll()
                        .anyRequest().authenticated()
                )

                // put the filter JwtAuthFilter before Spring filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) // activate CORS
            .csrf(csrf -> csrf.disable())    // disable CSRF
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Preflight requests (OPTIONS) is allowed
//                    .requestMatchers("/user/login", "/user/register").permitAll()
                    // Tambahkan endpoint profile, artwork, dan game ke sini:
                    .requestMatchers("/user/**", "/error").permitAll()
                    .anyRequest().authenticated()
            );
    return http.build();
    }
     */

    // Global CORS config for Custom Header problem (to allow header of username and game from front end)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // allow front end react to allow everything
        configuration.setAllowedOriginPatterns(List.of("*"));

        // allow HTTP methods needed
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // allow all custom headers
        configuration.setAllowedHeaders(Arrays.asList("username", "game", "Authorization", "Content-Type"));

        // allow sending credentials for cookie or token later
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}