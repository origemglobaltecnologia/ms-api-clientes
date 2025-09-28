package com.clientes.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTest {

    @Test
    void testOpenApiConfigLoads() {
        OpenApiConfig config = new OpenApiConfig();
        assertNotNull(config.apiInfo());
    }
}
