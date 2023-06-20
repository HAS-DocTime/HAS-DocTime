package com.spring.hasdoctime.security.configuration;

import com.spring.hasdoctime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * Configuration class for application-specific configurations.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Value("${jwt.bcrypt.password.encoder.strength}")
    final Optional<Integer> PASSWORD_ENCODER_STRENGTH = Optional.empty();

    private final UserRepository userRepository;

    /**
     * Custom UserDetailsService implementation that retrieves User records by email.
     *
     * @return the UserDetailsService bean
     */
    @Bean
    public UserDetailsService userDetailsService(){

        return username -> {
            return userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        };
    }

    /**
     * Configures the authentication provider for the application.
     *
     * @return the AuthenticationProvider bean
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }

    /**
     * Configures the AuthenticationManager bean.
     *
     * @param config the AuthenticationConfiguration
     * @return the AuthenticationManager bean
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

//    @Bean
//    public PasswordEncoder getPasswordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }

    /**
     * Configures the password encoder for user passwords.
     * Uses BCryptPasswordEncoder with the specified strength (if provided).
     *
     * @return the PasswordEncoder bean
     */
     @Bean
     public PasswordEncoder getPasswordEncoder() {
         return new BCryptPasswordEncoder(PASSWORD_ENCODER_STRENGTH.get());
     }
}
