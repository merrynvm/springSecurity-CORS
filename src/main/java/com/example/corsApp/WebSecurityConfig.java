package com.example.corsApp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration defaultCors = new CorsConfiguration().applyPermitDefaultValues();
        defaultCors.setAllowedOrigins(List.of("http://localhost:3000"));
        defaultCors.setAllowedMethods(List.of("*"));
        defaultCors.setAllowedHeaders(List.of("*"));

        //mai usare questi dato che aggiungono ma non impostano il primo valore.
        //è come se rimanesse il "*" quindi lascia abilitati tutti gli endpoint.
        //defaultCors.addAllowedOrigin("http://localhost:3005");
        //defaultCors.addAllowedHeader("*");
        //defaultCors.addAllowedMethod("*");

        //se ti serve anche specificare header/metodi/credentials:
        //defaultCors.setAllowedOrigins(List.of("http://localhost:3000")); per avere una lista di endpoint
        //defaultCors.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/custom/**", defaultCors);

        //per aggiungere altri pattern (endpoint) da cui accedere:
        //source.registerCorsConfiguration("admim/**", defaultCors);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                // 1) Attiva il filtro CORS con la tua configurazione
        http.cors(withDefaults())
                // 2) Sessione stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3) Disabilita CSRF (tipico per API stateless)
                .csrf(csrf -> csrf.disable())

                // 4) Autorizzazioni
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/custom/**").permitAll()

                        //puoi aggiungere altri matchers specifici prima, es:
                        //requestMatchers("/api/public/**").permitAll()

                        // 5) in fine anyRequest:
                        .anyRequest().authenticated());

        return http.build();
    }
}
