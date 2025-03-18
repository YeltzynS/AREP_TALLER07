package com.eci.arep.arep_taller07.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PostStream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "postStream", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Indica que esta es la parte "manejada" de la relación
    private List<Post> posts = new ArrayList<>();

    // Constructor sin argumentos (requerido por JPA)
    public PostStream() {}

    // Constructor con argumento id
    public PostStream(Long id) {
        this.id = id;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    // Método para agregar un post al stream
    public void addPost(Post post) {
        posts.add(post);
        post.setPostStream(this);
    }

    // Método para eliminar un post del stream
    public void removePost(Post post) {
        posts.remove(post);
        post.setPostStream(null);
    }
}