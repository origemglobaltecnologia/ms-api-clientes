package com.clientes.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.mockito.Mockito.*;

class WebConfigTest {

    private WebConfig webConfig;
    private CorsRegistry corsRegistry;

    @BeforeEach
    void setUp() {
        webConfig = new WebConfig();
        corsRegistry = mock(CorsRegistry.class);

        // Simula retorno encadeado dos métodos da API fluente
        when(corsRegistry.addMapping(anyString())).thenReturn(corsRegistry);
        when(corsRegistry.allowedOrigins(anyString())).thenReturn(corsRegistry);
        when(corsRegistry.allowedMethods(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(corsRegistry);
    }

    @Test
    void testCorsConfiguration() {
        WebMvcConfigurer configurer = webConfig.corsConfigurer();

        // Executa a configuração de CORS
        configurer.addCorsMappings(corsRegistry);

        // Verifica se os métodos foram chamados com os parâmetros corretos
        verify(corsRegistry).addMapping("/**");
        verify(corsRegistry).allowedOrigins("*");
        verify(corsRegistry).allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
