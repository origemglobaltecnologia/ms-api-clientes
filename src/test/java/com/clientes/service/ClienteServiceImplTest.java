package com.clientes.service;

import com.clientes.model.Cliente;
import com.clientes.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
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

    public ClienteServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarClienteComSucesso() {
        // Assumindo que o construtor de Cliente foi corrigido para (UUID, String, String, String, LocalDate)
        Cliente cliente = new Cliente(null, "Teste", "teste@email.com", "senha123", LocalDate.of(1990, 1, 1));
        when(clienteRepository.existsByEmail(cliente.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(cliente.getSenha())).thenReturn("senha_codificada");
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Correção 1: salvar() -> criarCliente()
        Cliente result = clienteService.criarCliente(cliente); // MUDANÇA AQUI

        assertEquals("senha_codificada", result.getSenha());
        verify(clienteRepository, times(1)).existsByEmail("teste@email.com");
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testListarTodosClientes() {
        // Correção 2: listarTodos() -> listarClientes()
        Cliente cliente1 = new Cliente(UUID.randomUUID(), "Nome1", "email1@test.com", "senha1", LocalDate.of(1990, 1, 1));
        List<Cliente> clientes = Arrays.asList(cliente1);
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.listarClientes(); // MUDANÇA AQUI

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testExistsByEmail() {
        // Correção 3: O teste deve chamar buscarPorEmail ou deve ser removido, pois existsByEmail está no Repository agora.
        // Se o objetivo é testar a lógica do Service que usa o Repositório:
        String email = "existe@email.com";
        when(clienteRepository.findByEmail(email)).thenReturn(Optional.of(new Cliente()));

        Optional<Cliente> result = clienteService.buscarPorEmail(email); // MUDANÇA AQUI

        assertTrue(result.isPresent());
        verify(clienteRepository, times(1)).findByEmail(email);
        
        // Se existsByEmail() foi realmente movido para o Repository, o teste DEVE ser alterado para buscarPorEmail ou outro método.
    }
}

