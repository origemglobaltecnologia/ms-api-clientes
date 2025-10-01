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

    // Método auxiliar usando o construtor correto: Cliente(UUID, String, String, String)
    private Cliente createTestCliente(UUID id, String nome) {
        return new Cliente(id, nome, nome + "@test.com", "senha123");
    }

    @Test
    void testCriarClienteComSucesso() {
        // Cliente de entrada (sem ID)
        Cliente clienteEntrada = new Cliente("Teste", "teste@email.com", "senha123");
        
        // Cliente que seria salvo e retornado (com ID e senha codificada)
        Cliente clienteSalvo = createTestCliente(UUID.randomUUID(), "Teste");

        when(clienteRepository.existsByEmail(clienteEntrada.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(clienteEntrada.getSenha())).thenReturn("senha_codificada");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvo);

        // CORREÇÃO: salvar() -> criarCliente()
        Cliente result = clienteService.criarCliente(clienteEntrada); 

        assertEquals("senha_codificada", result.getSenha());
        verify(clienteRepository, times(1)).existsByEmail("teste@email.com");
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testListarTodosClientes() {
        UUID id1 = UUID.randomUUID();
        Cliente cliente1 = createTestCliente(id1, "Nome1");
        List<Cliente> clientes = Arrays.asList(cliente1);
        when(clienteRepository.findAll()).thenReturn(clientes);

        // CORREÇÃO: listarTodos() -> listarClientes()
        List<Cliente> result = clienteService.listarClientes(); 

        assertFalse(result.isEmpty());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testNaoCriarClienteSeEmailExiste() {
        Cliente cliente = new Cliente("Existe", "existe@email.com", "senha123");
        
        // CORREÇÃO: O ServiceImpl usa existsByEmail para verificar
        when(clienteRepository.existsByEmail(cliente.getEmail())).thenReturn(true);

        // Espera-se que lance a exceção
        assertThrows(IllegalArgumentException.class, () -> clienteService.criarCliente(cliente));

        verify(clienteRepository, never()).save(any(Cliente.class));
    }
}

