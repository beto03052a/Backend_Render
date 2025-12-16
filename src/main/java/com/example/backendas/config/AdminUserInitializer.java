package com.example.backendas.config;

import com.example.backendas.entities.User;
import com.example.backendas.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * AdminUserInitializer
 * -----------------------------------------------------
 * ✔ Crea automáticamente un usuario administrador al iniciar la aplicación
 * ✔ Solo se crea si no existe previamente en la base de datos
 * ✔ Credenciales: admin123@gmail.com / 12345
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AdminUserInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            String adminEmail = "admin123@gmail.com";

            // Verificar si el usuario administrador ya existe
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User adminUser = User.builder()
                        .firstname("Admin")
                        .lastname("Sistema")
                        .email(adminEmail)
                        .password(passwordEncoder.encode("12345"))
                        .role("ROLE_ADMIN")
                        .build();

                userRepository.save(adminUser);
                log.info("✅ Usuario administrador creado exitosamente: {}", adminEmail);
            } else {
                log.info("ℹ️ Usuario administrador ya existe: {}", adminEmail);
            }
        };
    }
}
