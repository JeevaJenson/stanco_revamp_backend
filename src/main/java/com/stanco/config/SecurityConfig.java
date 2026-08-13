package com.stanco.config;

import com.stanco.security.CustomUserDetailsService;
import com.stanco.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        private final CustomUserDetailsService customUserDetailsService;

        @Bean
        public PasswordEncoder passwordEncoder() {

                return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {

                DaoAuthenticationProvider provider = new DaoAuthenticationProvider(
                                customUserDetailsService);

                provider.setPasswordEncoder(
                                passwordEncoder());

                return provider;
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration configuration)
                        throws Exception {

                return configuration
                                .getAuthenticationManager();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {

                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(
                                List.of(
                                                "http://localhost:5173"));

                configuration.setAllowedMethods(
                                List.of(
                                                "GET",
                                                "POST",
                                                "PUT",
                                                "DELETE",
                                                "PATCH",
                                                "OPTIONS"));

                configuration.setAllowedHeaders(
                                List.of("*"));

                configuration.setExposedHeaders(
                                List.of(
                                                "Authorization"));

                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

                source.registerCorsConfiguration(
                                "/**",
                                configuration);

                return source;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity http)
                        throws Exception {

                http

                                .csrf(csrf -> csrf.disable())

                                .cors(cors -> cors.configurationSource(
                                                corsConfigurationSource()))

                                .sessionManagement(session -> session.sessionCreationPolicy(
                                                SessionCreationPolicy.STATELESS))

                                .authenticationProvider(
                                                authenticationProvider())

                                .authorizeHttpRequests(auth -> auth

                                                .requestMatchers(
                                                                "/swagger",
                                                                "/swagger/**",
                                                                "/swagger-ui/**",
                                                                "/v3/api-docs/**",
                                                                "/api-docs/**")
                                                .permitAll()

                                                .requestMatchers(
                                                                "/api/auth/**")
                                                .permitAll()

                                                .requestMatchers(
                                                                "/api/users/**")
                                                .hasAnyRole(
                                                                "super_admin",
                                                                "admin",
                                                                "delivery_lead")

                                                .requestMatchers(
                                                                "/api/departments/**")
                                                .hasAnyRole(
                                                                "super_admin",
                                                                "admin")

                                                .requestMatchers(
                                                                "/api/designations/**")
                                                .hasAnyRole(
                                                                "super_admin",
                                                                "admin")

                                                .requestMatchers(
                                                                "/api/business-masters/**")
                                                .hasAnyRole(
                                                                "super_admin",
                                                                "admin")

                                                .requestMatchers(
                                                                "/api/rfh/**")
                                                .hasAnyRole(
                                                                "super_admin",
                                                                "admin",
                                                                "delivery_lead",
                                                                "recruiter")

                                                .requestMatchers(
                                                                "/api/rfh-revenue-details/**")
                                                .hasAnyRole(
                                                                "super_admin",
                                                                "admin",
                                                                "delivery_lead",
                                                                "recruiter")

                                                .requestMatchers(
                                                                "/api/candidates/**")
                                                .hasAnyRole(
                                                                "super_admin",
                                                                "admin",
                                                                "delivery_lead",
                                                                "recruiter")

                                                .anyRequest()
                                                .authenticated())

                                .addFilterBefore(
                                                jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}