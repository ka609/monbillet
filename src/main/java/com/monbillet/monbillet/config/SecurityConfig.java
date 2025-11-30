package com.monbillet.monbillet.config;

import com.monbillet.monbillet.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /** Authentication provider */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    /** Configuration de sécurité */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(auth -> auth
                // --- PAGES ACCESSIBLES SANS COMPTE ---
                .requestMatchers("/", "/login", "/register",
                        "/concerts/**", "/reservations/create/**",
                        "/css/**", "/js/**", "/images/**",
                        "/h2-console/**").permitAll()

                // --- ACTIONS RÉSERVÉES AUX UTILISATEURS CONNECTÉS ---
                .requestMatchers("/concerts/add", "/concerts/save",
                        "/concerts/edit/**", "/concerts/delete/**")
                .hasRole("USER")

                .anyRequest().authenticated()
        );

        // --- LOGIN STANDARD ---
        http.formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/concerts", true)
                .failureUrl("/login?error=true")
                .permitAll()
        );

        // --- LOGOUT ---
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/concerts")
                .permitAll()
        );

        // Autoriser la console H2
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}
