package com.eci.arep.arep_taller07.controller;

import com.eci.arep.arep_taller07.model.Post;
import com.eci.arep.arep_taller07.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        if (post.getContent().length() > 140) {
            throw new RuntimeException("El post no puede tener más de 140 caracteres.");
        }
        return postService.createPost(post);
    }

    @GetMapping("/stream/{postStreamId}")
    public List<Post> getPostsByStream(@PathVariable Long postStreamId) {
        return postService.getPostsByStream(postStreamId);
    }
}