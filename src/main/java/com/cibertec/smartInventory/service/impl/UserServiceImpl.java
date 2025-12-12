package com.cibertec.smartInventory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.cibertec.smartInventory.entity.User;
import com.cibertec.smartInventory.repository.UserRepository;
import com.cibertec.smartInventory.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User syncUser(User user) {
        // Buscamos si ya existe por su UID de Firebase
        Optional<User> existingUser = userRepository.findByFirebaseUid(user.getFirebaseUid());

        if (existingUser.isPresent()) {
            // Si existe, actualizamos sus datos (por si cambió foto o nombre en Google)
            User dbUser = existingUser.get();
            dbUser.setEmail(user.getEmail());
            dbUser.setFullName(user.getFullName());
            return userRepository.save(dbUser);
        } else {
            // Si no existe, es un usuario nuevo. Lo creamos.
            return userRepository.save(user);
        }
    }

    @Override
    public Optional<User> getUserByFirebaseUid(String uid) {
        return userRepository.findByFirebaseUid(uid);
    }
}