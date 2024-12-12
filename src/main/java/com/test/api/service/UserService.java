package com.test.api.service;

import com.test.api.model.User;

public interface UserService {
    User findOrCreateUser(String userId, String email);
    User getUserById(String userId);
}
