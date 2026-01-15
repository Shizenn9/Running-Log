package com.nel.workoutlog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Ignore static frontend resources completely so they load without 403
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
            "/", 
            "/index.html",
            "/login.html",
            "/register.html",
            "/**/*.css",
            "/**/*.js",
            "/**/*.html",
            "/favicon.ico",
            "/images/**"
        );
    }

    // Main security filter chain for APIs
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Disable CSRF for REST APIs
            .csrf(csrf -> csrf.disable())

            // Allow CORS (optional, can configure if needed)
            .cors(cors -> cors.disable())

            // Authorization rules
            .authorizeHttpRequests(auth -> auth
                // Allow login & register APIs
                .requestMatchers("/login", "/register").permitAll()

                // Protect workouts API
                .requestMatchers("/workouts/**").authenticated()

                // Any other requests require authentication
                .anyRequest().authenticated()
            )

            // Disable default login form
            .formLogin(form -> form.disable())

            // Disable HTTP Basic popup
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    // Password hashing
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
