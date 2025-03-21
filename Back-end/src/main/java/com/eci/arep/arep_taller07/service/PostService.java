package com.eci.arep.arep_taller07.service;

import com.eci.arep.arep_taller07.model.Post;
import com.eci.arep.arep_taller07.model.PostStream;
import com.eci.arep.arep_taller07.model.User;
import com.eci.arep.arep_taller07.repository.PostRepository;
import com.eci.arep.arep_taller07.repository.PostStreamRepository;
import com.eci.arep.arep_taller07.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostStreamRepository postStreamRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(Post post) {
        // Verificar que el PostStream existe
        PostStream postStream = postStreamRepository.findById(post.getPostStream().getId())
                .orElseThrow(() -> new RuntimeException("PostStream no encontrado con ID: " + post.getPostStream().getId()));

        // Verificar que el User existe
        User user = userRepository.findById(post.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User no encontrado con ID: " + post.getUser().getId()));

        // Asignar el PostStream y el User al Post
        post.setPostStream(postStream);
        post.setUser(user);

        // Guardar el Post
        return postRepository.save(post);
    }

    /**
     * Obtiene un post por su ID.
     *
     * @param id El ID del post.
     * @return El post encontrado, o null si no existe.
     */
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    /**
     * Obtiene todos los posts asociados a un stream.
     *
     * @param postStreamId El ID del stream.
     * @return Una lista de posts asociados al stream.
     */
    public List<Post> getPostsByStream(Long postStreamId) {
        return postRepository.findByPostStreamId(postStreamId);
    }
}
