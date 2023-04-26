package com.spring.hasdocTime.security;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig{

//    @Bean
//    protected InMemoryUserDetailsManager configureAuthentication() {
////        http.inMemoryAuthentication().withUser("username").password("bcrypt version").roles("ADMIN")
////                .and().withUser("usernamae1").password("bcrypt version").roles("ADMIN");
//
//        List<UserDetails> userDetails = new ArrayList<>();
//        List<GrantedAuthority> userRoles = new ArrayList<>();
//        userRoles.add(new SimpleGrantedAuthority("DOCTOR"));
//        userDetails.add(new User("username","password", userRoles));
//
//        List<GrantedAuthority> doctorRoles = new ArrayList<>();
//        doctorRoles.add(new SimpleGrantedAuthority("DOCTOR"));
//        userDetails.add(new User("username","password", doctorRoles));
//
//        List<GrantedAuthority> adminRoles = new ArrayList<>();
//        adminRoles.add(new SimpleGrantedAuthority("ADMIN"));
//        userDetails.add(new User("username","password", adminRoles))
//        return new InMemoryUserDetailsManager();
////        return http.build();
//    }

    @Bean
    protected UserDetailsService userDetailsService(){
        return new CustomUserDetailService();
    }
    @Bean
    public PasswordEncoder getPasswordEncode(){
        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    public PasswordEncoder getPasswordEncoder(){
//        return BCryptPasswordEncoder(10);
//    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // example 1

//        http.authorizeRequests()
//                .antMatchers("1").permitAll()
//                .antMatchers("2").hasAuthority("EMPLOYEE", "ADMIN")
//                .antMAtchers("3").hasRole("EMPLOYEE")
//                .and().formLogin();

        // example 2
        // done this to access all authenticated user
//        http.authorizeRequests()
//                .antMatchers("1","2").authenticated()
//                .and().formLogin();

        // example 3
        // get list from json file
        http.authorizeHttpRequests((authorize)-> authorize
                .requestMatchers(getAdminServices())
                        .hasAnyAuthority("ADMIN")
                .requestMatchers(getDoctorServices())
                        .hasAnyAuthority("DOCTOR")
                .requestMatchers(getPatientServices())
                        .hasAnyAuthority("PATIENT"))
                .formLogin();


        System.out.println("Bean out");
        return http.build();
    }

    private String[] getAdminServices(){
        InputStream fileStream = TypeReference.class.getResourceAsStream("/static/adminServices.json");
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

    private String[] getDoctorServices(){
        InputStream fileStream = TypeReference.class.getResourceAsStream("/static/doctorServices.json");
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

    private String[] getPatientServices(){
        InputStream fileStream = TypeReference.class.getResourceAsStream("/static/patientServices.json");
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