package com.example.avito.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // для учебного проекта — отключим CSRF, чтоб не мешал POST-формам
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // общедоступные ресурсы
                        .requestMatchers("/", "/register", "/login", "/css/**").permitAll()
                        .requestMatchers("/ads", "/ads/*").permitAll()
                        // всё, что меняет данные — только после логина
                        .requestMatchers("/ads/new", "/ads/**", "/payments/**", "/messages/**")
                            .authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}