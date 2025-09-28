package com.clientes.controller;

import com.clientes.model.Cliente;
import com.clientes.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    ClienteControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarClientes() {
        when(clienteService.listarTodos()).thenReturn(Collections.emptyList());
        assertTrue(clienteController.listarClientes().isEmpty());
    }

    @Test
    void testBuscarPorId() {
        Cliente cliente = new Cliente("Lucas", "lucas@email.com", "123456", LocalDate.of(1995, 7, 25));
        when(clienteService.buscarPorId(any())).thenReturn(Optional.of(cliente));

        assertTrue(clienteController.buscarPorId(cliente.getId()).getBody().isPresent());
    }

    @Test
    void testCriarCliente() {
        Cliente cliente = new Cliente("João", "joao@email.com", "123456", LocalDate.of(1990, 5, 10));
        when(clienteService.salvar(any())).thenReturn(cliente);

        Cliente salvo = clienteController.criarCliente(cliente).getBody();
        assertNotNull(salvo);
        assertEquals("João", salvo.getNome());
    }
}
