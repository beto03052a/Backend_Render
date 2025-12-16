package com.example.backendas.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig
 * -----------------------------------------------------
 * ✔ Configura Spring Security con JWT
 * ✔ Deshabilita CSRF (porque usamos token)
 * ✔ Aplica CORS global desde CorsConfig
 * ✔ Protege rutas excepto /api/auth/**
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                // Habilitar CORS global (configurado en CorsConfig)
                                .cors(cors -> cors.configure(http))
                                // Desactivar CSRF (no se usa con JWT)
                                .csrf(AbstractHttpConfigurer::disable)
                                // Definir rutas públicas y protegidas
                                .authorizeHttpRequests(auth -> auth
                                                // Rutas públicas (sin autenticación)
                                                .requestMatchers(
                                                                "/api/auth/login",
                                                                "/api/auth/register",
                                                                "/swagger-ui/**",
                                                                "/v3/api-docs/**",
                                                                "/swagger-resources/**")
                                                .permitAll()

                                                // Rutas de ADMIN (solo administradores del sistema)
                                                // GET de condominios permitido para todos los roles autenticados
                                                // (incluyendo RESIDENT)
                                                // POST, PUT, DELETE solo para ADMIN y ADMINISTRATOR (se validará en el
                                                // controlador si es necesario)
                                                .requestMatchers("/api/condominiums/**")
                                                .hasAnyRole("ADMIN", "ADMINISTRATOR")
                                                .requestMatchers("/api/administrators/**").hasRole("ADMIN")

                                                // Rutas de RESIDENT (residentes y administradores)
                                                .requestMatchers("/api/residents/**")
                                                .hasAnyRole("ADMIN", "ADMINISTRATOR", "RESIDENT")
                                                .requestMatchers("/api/units/**")
                                                .hasAnyRole("ADMIN", "ADMINISTRATOR", "RESIDENT")
                                                .requestMatchers("/api/payments/**")
                                                .hasAnyRole("ADMIN", "ADMINISTRATOR")
                                                .requestMatchers("/api/reservations/**")
                                                .hasAnyRole("ADMIN", "ADMINISTRATOR")
                                                .requestMatchers("/api/incidents/**")
                                                .hasAnyRole("ADMIN", "ADMINISTRATOR", "RESIDENT")

                                                // Ruta de perfil (cualquier usuario autenticado)
                                                .requestMatchers("/api/auth/me").authenticated()

                                                // Cualquier otra ruta requiere autenticación
                                                .anyRequest().authenticated())

                                // Política de sesión sin estado
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                // Registrar el AuthenticationProvider
                                .authenticationProvider(authenticationProvider)
                                // Registrar el filtro JWT antes del UsernamePasswordAuthenticationFilter
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
