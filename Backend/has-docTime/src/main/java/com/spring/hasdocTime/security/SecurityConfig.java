package com.spring.hasdocTime.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    //    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .and()
//                .oauth2Login()
//                ;
//
//        return http.build();
//    }
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configurer->
                        configurer
                                .requestMatchers(HttpMethod.GET, "/doctor").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/appointment").hasAnyRole("DOCTOR", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/timeSlot").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/doctor").hasAnyRole("DOCTOR", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/doctor").hasAnyRole("DOCTOR", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/doctor/**").hasAnyRole("ADMIN")
                );
        http.httpBasic();
        http.csrf().disable();
        return http.build();
    }
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails sanket = User.builder()
                .username("Sanket")
                .password("{noop}Sanket")
                .roles("ADMIN")
                .build();
        UserDetails mihir = User.builder()
                .username("Mihir")
                .password("{noop}Mihir")
                .roles("DOCTOR")
                .build();
        UserDetails arpit = User.builder()
                .username("Arpit")
                .password("{noop}Arpit")
                .roles("PATIENT")
                .build();
        UserDetails dhruv = User.builder()
                .username("Dhruv")
                .password("{noop}Dhruv")
                .roles("PATIENT")
                .build();
        return new InMemoryUserDetailsManager(sanket, mihir, arpit, dhruv);
    }
}