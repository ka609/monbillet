package com.monbillet.monbillet.service;

import com.monbillet.monbillet.config.UserDetailsImpl;
import com.monbillet.monbillet.entity.Role;
import com.monbillet.monbillet.entity.User;
import com.monbillet.monbillet.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /** Charger un utilisateur pour Spring Security */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + username));
        return new UserDetailsImpl(user);
    }

    /** Inscription utilisateur standard */
    public void registerUser(String nomComplet,
                             String email,
                             String telephone,
                             String adresse,
                             String username,
                             String rawPassword) {

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

    /** Récupérer tous les utilisateurs pour l'admin */
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /** Supprimer un utilisateur */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Utilisateur introuvable avec ID : " + id);
        }
        userRepository.deleteById(id);
    }

    /** Changer le rôle (ADMIN / USER) */
    public void updateRole(Long id, Role newRole) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable avec ID : " + id));
        user.setRole(newRole);
        userRepository.save(user);
    }

    /** Utilitaire : trouver un user par username */
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
