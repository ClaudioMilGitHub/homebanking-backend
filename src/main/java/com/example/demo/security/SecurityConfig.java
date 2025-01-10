package com.example.demo.security;

import com.example.demo.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                t-> {
                    t.requestMatchers("/all/**").permitAll();
                    t.requestMatchers("/user/**").authenticated();
                    t.requestMatchers("/staff/**").hasAnyRole(Role.STAFF.toString(), Role.ADMIN.toString());
                    t.requestMatchers("/admin/**").hasRole(Role.ADMIN.toString());
                    t.anyRequest().authenticated();
                });
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(t->t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.headers(t-> t.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();

    }
}