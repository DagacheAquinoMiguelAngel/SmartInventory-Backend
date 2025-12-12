package com.cibertec.smartInventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.smartInventory.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Método mágico: Spring crea el SQL "SELECT * FROM users WHERE firebase_uid = ?"
    Optional<User> findByFirebaseUid(String firebaseUid);
    
    // Para validar si ya existe al registrarse
    boolean existsByFirebaseUid(String firebaseUid);
}
