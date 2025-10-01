package com.clientes.controller;

import com.clientes.model.Cliente;
import com.clientes.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Método auxiliar usando o construtor correto: Cliente(UUID, String, String, String)
    private Cliente createTestCliente(UUID id, String nome) {
        return new Cliente(id, nome, nome + "@test.com", "senha123");
    }

    @Test
    void testCriarCliente_RetornaCreated() {
        // ID nulo para simular a criação, que é o construtor de 3 argumentos
        Cliente novoCliente = new Cliente("Novo", "novo@test.com", "senha123");
        
        // Cliente retornado pelo serviço já terá um ID
        Cliente clienteSalvo = createTestCliente(UUID.randomUUID(), "Novo");
        
        // CORREÇÃO: salvar() -> criarCliente()
        when(clienteService.criarCliente(any(Cliente.class))).thenReturn(clienteSalvo); 

        ResponseEntity<Cliente> response = clienteController.criarCliente(novoCliente); 

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(clienteSalvo.getEmail(), response.getBody().getEmail());
        verify(clienteService, times(1)).criarCliente(novoCliente);
    }

    @Test
    void testListarClientes_RetornaLista() {
        UUID id1 = UUID.randomUUID();
        Cliente cliente1 = createTestCliente(id1, "Nome1");
        List<Cliente> clientes = Arrays.asList(cliente1);
        
        // CORREÇÃO: listarTodos() -> listarClientes()
        when(clienteService.listarClientes()).thenReturn(clientes);

        // O Controller de produção retorna List<Cliente>
        List<Cliente> result = clienteController.listarClientes();

        assertEquals(1, result.size());
        verify(clienteService, times(1)).listarClientes();
    }
    
    @Test
    void testBuscarCliente_RetornaOK() {
        UUID id = UUID.randomUUID();
        Cliente cliente = createTestCliente(id, "Teste");
        
        when(clienteService.buscarPorId(id)).thenReturn(Optional.of(cliente));

        // O Controller chama buscarCliente (nome corrigido)
        ResponseEntity<Cliente> response = clienteController.buscarCliente(id); 

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente.getId(), response.getBody().getId());
        verify(clienteService, times(1)).buscarPorId(id);
    }
}

