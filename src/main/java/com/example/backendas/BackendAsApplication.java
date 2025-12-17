package com.example.backendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendAsApplication {

    public static void main(String[] args) {
        // Cargar variables del .env si existe (para desarrollo local)
        try {
            io.github.cdimascio.dotenv.Dotenv dotenv = io.github.cdimascio.dotenv.Dotenv.configure()
                .ignoreIfMissing()
                .load();
            dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        } catch (Exception e) {
            System.out.println("Nota: No se encontró archivo .env o falló la carga (normal en producción).");
        }

        SpringApplication.run(BackendAsApplication.class, args);
    }

}
