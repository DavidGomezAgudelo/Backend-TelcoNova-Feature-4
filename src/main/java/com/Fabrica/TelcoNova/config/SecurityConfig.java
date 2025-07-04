package com.Fabrica.TelcoNova.config;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.Fabrica.TelcoNova.model.UserModel;
import com.Fabrica.TelcoNova.repository.UserRepository;
import com.Fabrica.TelcoNova.service.JwtUtil;
import com.Fabrica.TelcoNova.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserService userService; 

    public SecurityConfig(JwtUtil jwtUtil, UserRepository userRepository, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll() // Correcto para la depuración de CORS
            )
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(authorization -> authorization
                    .baseUri("/oauth2/authorization")
                )
                .redirectionEndpoint(redirection -> redirection
                    .baseUri("/login/oauth2/code/google")
                )
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(new DefaultOAuth2UserService()) 
                )
                .successHandler((request, response, authentication) -> {
                    DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
                    String email = principal.getAttribute("email");
                    String name = principal.getAttribute("name");
                    UserModel user = userService.findOrCreateUser(email, name);
                    String token = jwtUtil.generateToken(email, name);
                    String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
                    String frontendUrl = "https://backend-telconova-feature-4.onrender.com/auth/success"; 
                    response.sendRedirect(frontendUrl + "?token=" + encodedToken);
                })
            );
            
    
        http.addFilterBefore(new JwtTokenFilter(jwtUtil, userRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}