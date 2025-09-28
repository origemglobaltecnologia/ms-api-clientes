package com.clientes.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    @Test
    void testPasswordEncoder() {
        SecurityConfig config = new SecurityConfig();
        PasswordEncoder encoder = config.passwordEncoder();

        String senha = "123456";
        String encoded = encoder.encode(senha);

        assertNotEquals(senha, encoded);
        assertTrue(encoder.matches(senha, encoded));
    }
}
