package com.w.ever.files.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF protection for stateless applications
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll()  // Allow public access to login and register
                        .anyRequest().authenticated()  // Require authentication for all other requests
                )
                .formLogin(Customizer.withDefaults()) // This line ensures a basic form login setup if needed
                .httpBasic(Customizer.withDefaults());  // Basic authentication, adjust as needed
        return http.build();
    }
}
