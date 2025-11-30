package com.monbillet.monbillet.service;

import com.monbillet.monbillet.entity.Role;
import com.monbillet.monbillet.entity.User;
import com.monbillet.monbillet.repository.UserRepository;
import com.monbillet.monbillet.config.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /** Inscription utilisateur simple */
    public void registerUser(String nomComplet, String email, String telephone,
                             String adresse, String username, String rawPassword) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Nom d'utilisateur déjà pris");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email déjà utilisé");
        }

        User user = new User();
        user.setNomComplet(nomComplet);
        user.setEmail(email);
        user.setTelephone(telephone);
        user.setAdresse(adresse);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    /** Spring Security */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));

        return new UserDetailsImpl(user);
    }
    /** Recherche utilitaire */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

}
