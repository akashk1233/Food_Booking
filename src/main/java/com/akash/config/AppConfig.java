package com.akash.config;

import com.akash.enums.USER_ROLE;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@Slf4j
public class AppConfig {

    @Autowired
    private CustomeAccessDeniedHandler customeAccessDeniedHandler;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        log.info("request111111 {}",http.toString());
        http.sessionManagement(managment->managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e-> e.accessDeniedHandler(customeAccessDeniedHandler).authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .authorizeHttpRequests(Authorise->
                        Authorise.requestMatchers("/api/admin/**").hasRole((USER_ROLE.ADMIN.name()))
                                .requestMatchers("/api/**").authenticated()
                                .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf->csrf.disable())
                .cors(cors->cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {

        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg= new CorsConfiguration();
                cfg.setAllowedOrigins(Arrays.asList(
                        "http://localhost:8080"
                ));
//                cfg.setAllowedMethods((List<String>) Collections.singleton("*"));
                cfg.setAllowCredentials(true);
//                cfg.setAllowedHeaders((List<String>) Collections.singleton("*"));
//                cfg.setExposedHeaders(Arrays.asList("Authorization"));
                cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                cfg.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}