package com.worktracking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public UserDetailsService userDetailsService() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // Создаем шифратор паролей

        return new InMemoryUserDetailsManager(
                User.withUsername("guest")
                        .password(encoder.encode("guestpass")) // Шифруем пароль
                        .roles("GUEST")
                        .build(),
                User.withUsername("manager")
                        .password(encoder.encode("managerpass"))
                        .roles("MANAGER")
                        .build(),
                User.withUsername("admin")
                        .password(encoder.encode("adminpass"))
                        .roles("ADMIN")
                        .build()
        );
    }


    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF для тестирования
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll() // Доступ открыт всем
                        .requestMatchers(HttpMethod.GET, "/tasks/**", "/worktime/**").permitAll() // Гость может смотреть задачи
                        .requestMatchers("/worktime/**", "/tasks/**").hasAnyRole("MANAGER", "ADMIN") // Менеджеры и админы управляют задачами
                        .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN") // Только админ может создавать пользователей
                        .requestMatchers(HttpMethod.GET, "/worktime/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/worktime/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN")
                        .anyRequest().authenticated() //  Все остальные запросы требуют авторизации!
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}


