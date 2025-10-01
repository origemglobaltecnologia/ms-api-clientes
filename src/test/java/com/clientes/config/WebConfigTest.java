package com.clientes.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.mockito.Mockito.*;

class WebConfigTest {

    // Instância da classe que contém a configuração
    private WebConfig webConfig;
    
    // Mock do registro principal de CORS (o objeto que o Spring passa)
    private CorsRegistry corsRegistry;
    
    // Mock do objeto de registro de CORS para um determinado mapeamento
    private CorsRegistration corsRegistration;

    @BeforeEach
    void setUp() {
        // Inicialize o WebConfig, que é a classe sob teste
        webConfig = new WebConfig();
        
        // Crie os mocks
        corsRegistry = mock(CorsRegistry.class);
        corsRegistration = mock(CorsRegistration.class);

        /* * 1. Mock: Quando addMapping() é chamado no corsRegistry, 
         * deve retornar nosso mock de CorsRegistration.
         */
        when(corsRegistry.addMapping(anyString())).thenReturn(corsRegistration);
        
        /* * 2. Mock: Simular a API fluente (encadeada) no CorsRegistration.
         * Quando allowedOrigins() ou allowedMethods() é chamado, deve retornar ele mesmo.
         * Note que o método thenReturn agora recebe CorsRegistration, não CorsRegistry!
         */
        when(corsRegistration.allowedOrigins(ArgumentMatchers.<String[]>any()))
                .thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods(ArgumentMatchers.<String[]>any()))
                .thenReturn(corsRegistration);
    }

    @Test
    void testCorsConfiguration() {
        // Obter a configuração do Spring
        WebMvcConfigurer configurer = webConfig.corsConfigurer();

        // Executa a configuração de CORS chamando o método configurador
        configurer.addCorsMappings(corsRegistry);

        // Verifica se os métodos foram chamados com os parâmetros corretos

        // 1. Verifica se addMapping foi chamado no Registry
        verify(corsRegistry).addMapping("/**");
        
        // 2. Verifica se allowedOrigins foi chamado no Registration retornado
        verify(corsRegistration).allowedOrigins("*");
        
        // 3. Verifica se allowedMethods foi chamado no Registration
        verify(corsRegistration).allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}

