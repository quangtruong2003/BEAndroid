package com.quangtruong.be.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/auth/employee/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll() // Cho phép đăng ký và đăng nhập
                        .requestMatchers("/api/cart/**").authenticated() // Giỏ hàng cần đăng nhập
                        .requestMatchers("/api/products/**", "/api/categories/**", "/api/suppliers/**").permitAll() // Cho phép xem sản phẩm, danh mục, nhà cung cấp
                        .requestMatchers("/api/**").authenticated() // Các API còn lại cần đăng nhập
                        .anyRequest().permitAll() // Các request khác cho phép hết
                )
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfs = new CorsConfiguration();
                cfs.setAllowedOrigins(Arrays.asList(
                        "http://localhost:3000"
                ));
                cfs.setAllowedMethods(Collections.singletonList("*"));
                cfs.setAllowCredentials(true);
                cfs.setAllowedHeaders(Collections.singletonList("*"));
                cfs.setExposedHeaders(Arrays.asList("Authorization"));
                cfs.setMaxAge(3600L);
                return cfs;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}