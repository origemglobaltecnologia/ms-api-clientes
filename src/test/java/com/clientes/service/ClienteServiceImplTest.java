package com.clientes.service;

import com.clientes.model.Cliente;
import com.clientes.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Método auxiliar (Pode ser mantido, mas não é usado no teste corrigido abaixo)
    private Cliente createTestCliente(UUID id, String nome, String senha) {
        return new Cliente(id, nome, nome + "@test.com", senha);
    }

    @Test
    void testCriarClienteComSucesso() {
        // 1. Cliente de entrada (sem ID, com senha clara)
        Cliente clienteEntrada = new Cliente("Teste", "teste@email.com", "senha123");
        
        // 2. Mockamos o que o encoder fará
        String senhaCodificada = "senha_codificada";
        when(passwordEncoder.encode(clienteEntrada.getSenha())).thenReturn(senhaCodificada);
        
        // 3. Criamos o objeto que o Repository VAI RETORNAR.
        // Ele DEVE ter a senha codificada para simular o comportamento real do serviço.
        Cliente clienteSalvoMock = new Cliente(
            UUID.randomUUID(), 
            "Teste", 
            "teste@email.com", 
            senhaCodificada // USANDO A SENHA CODIFICADA AQUI
        );

        when(clienteRepository.existsByEmail(clienteEntrada.getEmail())).thenReturn(false);
        
        // 4. O Repository MOCK retorna o objeto com a senha codificada.
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvoMock);

        // Ação: Chama o método real
        Cliente result = clienteService.criarCliente(clienteEntrada); 

        // 5. Verificação: O resultado final (o objeto retornado pelo serviço)
        // deve ter a senha codificada.
        assertEquals(senhaCodificada, result.getSenha());
        verify(clienteRepository, times(1)).existsByEmail("teste@email.com");
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testListarTodosClientes() {
        UUID id1 = UUID.randomUUID();
        // Usamos o auxiliar, com a senha clara, pois é apenas um objeto mock
        Cliente cliente1 = createTestCliente(id1, "Nome1", "senha123"); 
        List<Cliente> clientes = Arrays.asList(cliente1);
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.listarClientes(); 

        assertFalse(result.isEmpty());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testNaoCriarClienteSeEmailExiste() {
        Cliente cliente = new Cliente("Existe", "existe@email.com", "senha123");
        
        when(clienteRepository.existsByEmail(cliente.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> clienteService.criarCliente(cliente));

        verify(clienteRepository, never()).save(any(Cliente.class));
    }
}

