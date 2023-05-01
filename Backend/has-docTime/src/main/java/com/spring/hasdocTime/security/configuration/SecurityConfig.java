package com.spring.hasdocTime.security.configuration;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.hasdocTime.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

//    @Bean
//    protected UserDetailsService userDetailsService(){
//        return new CustomUserDetailService();
//    }



    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Old Deprecated code
//         example 1
//
//        http.authorizeRequests()
//                .antMatchers("1").permitAll()
//                .antMatchers("2").hasAuthority("EMPLOYEE", "ADMIN")
//                .antMAtchers("3").hasRole("EMPLOYEE")
//                .and().formLogin();
//
//         example 2
//         done this to access all authenticated user
//        http.authorizeRequests()
//                .antMatchers("1","2").authenticated()
//                .and().formLogin();

        // example 3
        // get list from json file
        http.cors().and().csrf().disable()
        .authorizeHttpRequests((authorize)-> authorize
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/chronicIllness").permitAll()
                .requestMatchers(getServices("/static/adminServices.json"))
                        .hasAnyAuthority("ADMIN")
                .requestMatchers(getServices("/static/doctorServices.json"))
                        .hasAnyAuthority("DOCTOR")
                .requestMatchers(getServices("/static/patientServices.json"))
                        .hasAnyAuthority("PATIENT"))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


//        System.out.println("Bean out");
        return http.build();
    }

    private String[] getServices(String location){
        InputStream fileStream = TypeReference.class.getResourceAsStream(location);
        ObjectMapper mapper = new ObjectMapper();
        List<String> urlList = new ArrayList<>();
        try {
            urlList = mapper.readValue(fileStream, ArrayList.class);
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return urlList.stream().toArray(String[]::new);
    }


}