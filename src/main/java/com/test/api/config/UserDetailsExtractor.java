package com.test.api.config;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsExtractor {

    public String extractUsername(FirebaseToken token) {
        return token.getEmail(); // or , token.getUid(), etc.
    }
}
