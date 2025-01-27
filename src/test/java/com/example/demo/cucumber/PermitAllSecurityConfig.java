package com.example.demo.cucumber;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class PermitAllSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrfconf -> csrfconf.disable())
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().permitAll())
        ;
        return http.build();
    }
}