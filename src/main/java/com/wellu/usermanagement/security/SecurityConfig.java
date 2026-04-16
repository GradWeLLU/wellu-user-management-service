package com.wellu.usermanagement.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.wellu.common.security.JwtConfig;
import org.wellu.common.security.JwtAuthFilter;
import com.wellu.usermanagement.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.wellu.common.security.JwtService;
import org.wellu.common.security.CustomAuthenticationEntryPoint;
import org.wellu.common.security.CustomAccessDeniedHandler;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtService jwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expiration
    ) {
        JwtConfig config = JwtConfig.builder()
                .secret(secret)
                .expiration(expiration)
                .build();

        return new JwtService(config);
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtService jwtService) {
        return new JwtAuthFilter(jwtService, userId -> {
            CustomUserPrincipal principal = new CustomUserPrincipal(userId);

            return new UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    principal.getAuthorities()
            );
        });
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthFilter jwtAuthFilter,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login", "/user/register").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint(ObjectMapper objectMapper) {
        return new CustomAuthenticationEntryPoint(objectMapper);
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler(ObjectMapper objectMapper) {
        return new CustomAccessDeniedHandler(objectMapper);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByEmail(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        List.of() // add roles if needed
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}

