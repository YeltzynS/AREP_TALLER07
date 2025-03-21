
package com.eci.arep.arep_taller07.repository;


import com.eci.arep.arep_taller07.model.PostStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostStreamRepository extends JpaRepository<PostStream, Long> {
}