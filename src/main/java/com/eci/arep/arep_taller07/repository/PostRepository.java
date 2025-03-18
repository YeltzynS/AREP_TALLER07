
package com.eci.arep.arep_taller07.repository;


import com.eci.arep.arep_taller07.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPostStreamId(Long postStreamId);
}