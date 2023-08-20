package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll() // Permitir acceso a /login
                .antMatchers(HttpMethod.GET, "/user/**").hasAnyRole("Admin", "User_human", "User_juridic")
                .antMatchers(HttpMethod.POST, "/user/**").hasRole("Admin")
                .antMatchers(HttpMethod.GET, "/product/**").hasAnyRole("Admin", "User_human", "User_juridic")
                .antMatchers(HttpMethod.POST, "/product/**").hasRole("Admin")
                .antMatchers(HttpMethod.GET, "/document/**").hasAnyRole("Admin", "User_human", "User_juridic")
                .antMatchers(HttpMethod.POST, "/document/userpay").hasAnyRole("User_human", "User_juridic")
                .antMatchers(HttpMethod.PUT, "/document/{id}/pay").hasAnyRole("User_human", "User_juridic")
                .antMatchers(HttpMethod.POST, "/document/**").hasRole("Admin")
                .antMatchers(HttpMethod.GET, "/tax/**").hasAnyRole("Admin", "User_human", "User_juridic")
                .antMatchers(HttpMethod.GET, "/discounts/**").hasAnyRole("Admin", "User_human", "User_juridic")
                .antMatchers(HttpMethod.PUT).hasRole("Admin")
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        http.formLogin().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
