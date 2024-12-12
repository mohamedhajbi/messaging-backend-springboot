package com.test.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public UserDetailsExtractor userDetailsExtractor() {
        return new UserDetailsExtractor();
    }
}
