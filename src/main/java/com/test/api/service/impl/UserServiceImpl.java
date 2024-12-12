package com.test.api.service.impl;

import com.test.api.model.User;
import com.test.api.repository.UserRepository;
import com.test.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findOrCreateUser(String userId, String email) {
        return userRepository.findById(userId).orElseGet(() -> {
            User newUser = new User(userId, email);
            return userRepository.save(newUser);
        });
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
