
package com.eci.arep.arep_taller07.repository;

import com.eci.arep.arep_taller07.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
