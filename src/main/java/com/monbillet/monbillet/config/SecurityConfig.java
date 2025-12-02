package com.monbillet.monbillet.config;

import com.monbillet.monbillet.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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

    /** SuccessHandler pour redirection selon rôle */
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin) {
                response.sendRedirect("/admin/dashboard");
            } else {
                response.sendRedirect("/concerts");
            }
        };
    }

    /** Configuration de sécurité */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // --- Désactivation CSRF pour simplification (attention production) ---
        http.csrf(csrf -> csrf.disable());

        // --- Provider d'authentification ---
        http.authenticationProvider(authenticationProvider());

        // --- Autorisations selon rôle ---
        http.authorizeHttpRequests(auth -> auth
                // Pages publiques
                .requestMatchers("/", "/login", "/register",
                        "/css/**", "/js/**", "/images/**",
                        "/h2-console/**").permitAll()

                // ADMIN : dashboard, gestion utilisateurs, concerts et réservations
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // UTILISATEUR : réserver, ajouter/éditer/supprimer concerts (si ton business le permet)
                .requestMatchers("/concerts/add", "/concerts/save",
                        "/concerts/edit/**", "/concerts/delete/**",
                        "/reservations/create/**").hasRole("USER")

                // Toute autre requête nécessite authentification
                .anyRequest().authenticated()
        );

        // --- Formulaire de login ---
        http.formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(successHandler()) // redirection dynamique
                .failureUrl("/login?error=true")
                .permitAll()
        );

        // --- Logout ---
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
        );

        // --- H2 console ---
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}
