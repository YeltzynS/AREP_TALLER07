package com.eci.arep.arep_taller07.service;

import com.eci.arep.arep_taller07.model.PostStream;
import com.eci.arep.arep_taller07.repository.PostStreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostStreamService {
    @Autowired
    private PostStreamRepository postStreamRepository;

    // Método para obtener un PostStream por su ID
    public PostStream getStream(Long id) {
        Optional<PostStream> streamOptional = postStreamRepository.findById(id);
        return streamOptional.orElse(null); // Devuelve null si no se encuentra el stream
    }

    // Método para crear un nuevo PostStream
    public PostStream createStream(PostStream postStream) {
        return postStreamRepository.save(postStream);
    }
}