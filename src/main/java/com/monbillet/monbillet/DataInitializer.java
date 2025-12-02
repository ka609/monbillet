package com.monbillet.monbillet;

import com.monbillet.monbillet.entity.Role;
import com.monbillet.monbillet.entity.User;
import com.monbillet.monbillet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("admin").isEmpty()) {

            User admin = new User();
            admin.setNomComplet("Administrateur");
            admin.setEmail("admin@monbillet.com");
            admin.setTelephone("0000");
            admin.setAdresse("System");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            System.out.println("------ ADMIN CREATED (admin/admin123) ------");
        }
    }
}
