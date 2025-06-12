package com.Fabrica.TelcoNova.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Fabrica.TelcoNova.service.JwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    // --- 1. ELIMINAR OAUTH2USERSERVICE DEL CONSTRUCTOR ---
    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // --- 2. ELIMINAR EL BEAN SEPARADO PARA OAUTH2USERSERVICE ---
    // Ya no es necesario, lo crearemos donde se usa.
    /*
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return new DefaultOAuth2UserService();
    }
    */
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/oauth2/**", "/login/oauth2/code/google").permitAll()
                .requestMatchers("/graphiql", "/vendor/graphiql/**", "/graphql/schema.json").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(authorization -> authorization
                    .baseUri("/oauth2/authorization")
                )
                .redirectionEndpoint(redirection -> redirection
                    .baseUri("/login/oauth2/code/google")
                )
                .userInfoEndpoint(userInfo -> userInfo
                    // --- 3. CREAR Y USAR LA INSTANCIA DIRECTAMENTE AQUÍ ---
                    .userService(new DefaultOAuth2UserService()) 
                )
                .successHandler((request, response, authentication) -> {
                    DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
                    String email = principal.getAttribute("email");
                    String name = principal.getAttribute("name");
                    String token = jwtUtil.generateToken(email, name);
                    response.sendRedirect("/auth/success?token=" + token);
                })
            );
            
        http.addFilterBefore(new JwtTokenFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}