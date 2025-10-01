package com.clientes.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenApiConfigTest {

    @Test
    void testOpenApiConfigLoads() {
        OpenApiConfig config = new OpenApiConfig();
        
        // CORREÇÃO: Chama o novo método que configura o objeto OpenAPI.
        // O nome do método é 'customOpenAPI' se você seguiu a convenção Springdoc.
        OpenAPI openApi = config.customOpenAPI();
        
        // Verifica se o objeto OpenAPI e as informações da API (Info) foram criados corretamente.
        assertNotNull(openApi);
        assertNotNull(openApi.getInfo());
    }
}

