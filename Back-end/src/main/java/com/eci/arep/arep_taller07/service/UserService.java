package com.eci.arep.arep_taller07.service;

import com.eci.arep.arep_taller07.model.User;
import com.eci.arep.arep_taller07.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Nuevo método para obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}