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

    // Método auxiliar para criar clientes de teste
    private Cliente createTestCliente(UUID id, String nome, String senha) {
        return new Cliente(id, nome, nome + "@test.com", senha);
    }

    @Test
    void testCriarClienteComSucesso() {
        // Cliente de entrada (senha clara)
        Cliente clienteEntrada = new Cliente("Teste", "teste@email.com", "senha123");

        // Mock do encoder para retornar a senha codificada
        String senhaCodificada = "senha_codificada";
        when(passwordEncoder.encode(clienteEntrada.getSenha())).thenReturn(senhaCodificada);

        // Mock do Repository: retorna um cliente com senha codificada
        Cliente clienteSalvoMock = new Cliente(
            UUID.randomUUID(),
            "Teste",
            "teste@email.com",
            senhaCodificada
        );

        when(clienteRepository.existsByEmail(clienteEntrada.getEmail())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvoMock);

        // Chama o método real do serviço
        Cliente result = clienteService.criarCliente(clienteEntrada);

        // Verificações
        assertEquals(senhaCodificada, result.getSenha());
        verify(clienteRepository, times(1)).existsByEmail("teste@email.com");
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testListarTodosClientes() {
        UUID id1 = UUID.randomUUID();
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
