package com.eci.arep.arep_taller07.controller;

import com.eci.arep.arep_taller07.model.Post;
import com.eci.arep.arep_taller07.model.PostStream;
import com.eci.arep.arep_taller07.model.User;
import com.eci.arep.arep_taller07.service.PostService;
import com.eci.arep.arep_taller07.service.PostStreamService;
import com.eci.arep.arep_taller07.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostStreamService postStreamService;

    // ID del PostStream compartido (puedes cambiarlo según tu lógica)
    private static final Long SHARED_POST_STREAM_ID = 1L;

    @PostMapping("/register")
    public ResponseEntity<?> registerUserAndCreatePost(@RequestBody Map<String, String> request) {
        // Extraer datos del usuario
        String name = request.get("name");
        String email = request.get("email");
        String password = request.get("password");
        String postContent = request.get("postContent");

        // Validar longitud del post
        if (postContent.length() > 140) {
            throw new RuntimeException("El post no puede tener más de 140 caracteres.");
        }

        // Crear el usuario
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        User savedUser = userService.createUser(user);

        // Obtener el PostStream compartido (o crearlo si no existe)
        PostStream sharedPostStream = postStreamService.getStream(SHARED_POST_STREAM_ID);
        if (sharedPostStream == null) {
            // Crear un nuevo PostStream si no existe
            sharedPostStream = new PostStream();
            sharedPostStream.setId(SHARED_POST_STREAM_ID); // Asignar el ID 1
            sharedPostStream.setName("Stream Principal");
            sharedPostStream = postStreamService.createStream(sharedPostStream);
        }

        // Crear el post asociado al usuario y al PostStream compartido
        Post post = new Post();
        post.setContent(postContent);
        post.setUser(savedUser);
        post.setPostStream(sharedPostStream);
        Post savedPost = postService.createPost(post);

        // Devolver una respuesta con el usuario y el post creados
        Map<String, Object> response = new HashMap<>();
        response.put("user", savedUser);
        response.put("post", savedPost);
        return ResponseEntity.ok(response);
    }
}