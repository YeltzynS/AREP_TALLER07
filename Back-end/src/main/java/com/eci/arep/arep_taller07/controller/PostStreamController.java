package com.eci.arep.arep_taller07.controller;

import com.eci.arep.arep_taller07.model.PostStream;
import com.eci.arep.arep_taller07.service.PostStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stream")
public class PostStreamController {
    @Autowired
    private PostStreamService postStreamService;

    // Obtener un PostStream por su ID
    @GetMapping("/{id}")
    public PostStream getStream(@PathVariable Long id) {
        return postStreamService.getStream(id);
    }

    // Crear un nuevo PostStream
    @PostMapping
    public PostStream createStream(@RequestBody PostStream postStream) {
        return postStreamService.createStream(postStream);
    }
}