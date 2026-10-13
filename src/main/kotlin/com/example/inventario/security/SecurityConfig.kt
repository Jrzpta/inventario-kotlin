package com.example.inventario.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE,"/api/productos/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET,"/api/**").hasAnyRole("ADMIN","BODEGUERO",
                        "CONSULTA")
                    .requestMatchers("/api/**").hasAnyRole("ADMIN","BODEGUERO")
                    .anyRequest().denyAll()
            }
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }
}