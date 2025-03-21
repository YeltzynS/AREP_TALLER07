package com.eci.arep.arep_taller07;

import com.eci.arep.arep_taller07.model.PostStream;
import com.eci.arep.arep_taller07.service.PostStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppSpringBoot implements CommandLineRunner {

    @Autowired
    private PostStreamService postStreamService;

    public static void main(String[] args) {
        SpringApplication.run(AppSpringBoot.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear un PostStream por defecto si no existe
        PostStream defaultStream = postStreamService.getStream(1L);
        if (defaultStream == null) {
            PostStream newStream = new PostStream();
            newStream.setId(1L); // Asignar el ID 1
            newStream.setName("Stream Principal");
            postStreamService.createStream(newStream);
            System.out.println("PostStream por defecto creado con ID 1");
        }
    }
}