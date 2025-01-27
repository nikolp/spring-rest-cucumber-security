package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrfconf -> csrfconf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/actuator/**").permitAll()
                                .anyRequest().authenticated())
                .formLogin(frmconf -> frmconf.permitAll())
        ;
        return http.build();
    }
}
