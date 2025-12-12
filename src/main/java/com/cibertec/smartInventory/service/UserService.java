package com.cibertec.smartInventory.service;

import java.util.Optional;

import com.cibertec.smartInventory.entity.User;

public interface UserService {
	// Sincroniza el usuario de Firebase con tu BD MySQL
    User syncUser(User user);
    Optional<User> getUserByFirebaseUid(String uid);
}
