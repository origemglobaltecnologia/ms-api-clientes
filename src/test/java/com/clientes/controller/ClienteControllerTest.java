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

import java.time.LocalDate;
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
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    private Cliente createTestCliente(UUID id, String nome) {
        // Assume o construtor corrigido na classe Cliente: (UUID, String, String, String, LocalDate)
        return new Cliente(id, nome, nome + "@test.com", "senha123", LocalDate.of(1990, 1, 1));
    }

    @Test
    void testCriarCliente_RetornaCreated() {
        Cliente novoCliente = createTestCliente(null, "Novo");
        Cliente clienteSalvo = createTestCliente(UUID.randomUUID(), "Novo");
        
        // Mock do Service
        when(clienteService.criarCliente(any(Cliente.class))).thenReturn(clienteSalvo); 

        // Chamada ao Controller
        ResponseEntity<Cliente> response = clienteController.criarCliente(novoCliente); 

        // Verificações
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(clienteSalvo, response.getBody());
        verify(clienteService, times(1)).criarCliente(novoCliente);
    }

    @Test
    void testListarClientes_RetornaLista() {
        UUID id1 = UUID.randomUUID();
        Cliente cliente1 = createTestCliente(id1, "Nome1");
        List<Cliente> clientes = Arrays.asList(cliente1);
        
        // Mock do Service
        when(clienteService.listarClientes()).thenReturn(clientes);

        // Chamada ao Controller, que agora retorna apenas List<Cliente> (CORREÇÃO)
        List<Cliente> result = clienteController.listarClientes();

        // Verificações
        assertEquals(1, result.size());
        assertEquals(cliente1, result.get(0));
        verify(clienteService, times(1)).listarClientes();
    }
    
    @Test
    void testBuscarCliente_RetornaOK() {
        UUID id = UUID.randomUUID();
        Cliente cliente = createTestCliente(id, "Teste");
        
        // Mock do Service (Retorna Optional<Cliente>)
        when(clienteService.buscarPorId(id)).thenReturn(Optional.of(cliente));

        // Chamada ao Controller (CORREÇÃO do nome do método para buscarCliente)
        ResponseEntity<Cliente> response = clienteController.buscarCliente(id); 

        // Verificações
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
        verify(clienteService, times(1)).buscarPorId(id);
    }

    @Test
    void testBuscarCliente_NaoEncontrado() {
        UUID id = UUID.randomUUID();

        // Mock do Service (Retorna Optional vazio)
        when(clienteService.buscarPorId(id)).thenReturn(Optional.empty());

        // Chamada ao Controller (CORREÇÃO do nome do método para buscarCliente)
        ResponseEntity<Cliente> response = clienteController.buscarCliente(id);

        // Verificações
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(clienteService, times(1)).buscarPorId(id);
    }

    @Test
    void testBuscarPorEmail_RetornaOK() {
        String email = "existe@email.com";
        UUID id = UUID.randomUUID();
        Cliente cliente = createTestCliente(id, "EmailTeste");
        cliente.setEmail(email);

        when(clienteService.buscarPorEmail(email)).thenReturn(Optional.of(cliente));

        ResponseEntity<Cliente> response = clienteController.buscarPorEmail(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
        verify(clienteService, times(1)).buscarPorEmail(email);
    }
    
    @Test
    void testAtualizarCliente_RetornaOK() {
        UUID id = UUID.randomUUID();
        Cliente dadosAtualizados = createTestCliente(null, "Atualizado");
        Cliente clienteAtualizado = createTestCliente(id, "Atualizado");
        
        when(clienteService.atualizarCliente(eq(id), any(Cliente.class))).thenReturn(clienteAtualizado);

        ResponseEntity<Cliente> response = clienteController.atualizarCliente(id, dadosAtualizados);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteAtualizado.getNome(), response.getBody().getNome());
        verify(clienteService, times(1)).atualizarCliente(id, dadosAtualizados);
    }

    @Test
    void testExcluirCliente_RetornaNoContent() {
        UUID id = UUID.randomUUID();
        
        // Não precisamos simular comportamento, apenas garantir que a chamada não lance exceção
        doNothing().when(clienteService).excluirCliente(id);

        ResponseEntity<Void> response = clienteController.excluirCliente(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clienteService, times(1)).excluirCliente(id);
    }
}

