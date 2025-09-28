package com.clientes.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testGettersAndSetters() {
        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setEmail("joao@email.com");
        cliente.setSenha("senha123");
        cliente.setDataNascimento(LocalDate.of(1990, 5, 10));

        assertEquals("João", cliente.getNome());
        assertEquals("joao@email.com", cliente.getEmail());
        assertEquals("senha123", cliente.getSenha());
        assertEquals(LocalDate.of(1990, 5, 10), cliente.getDataNascimento());
    }

    @Test
    void testEqualsAndHashCode() {
        Cliente cliente1 = new Cliente("João", "joao@email.com", "123456", LocalDate.of(1990, 5, 10));
        Cliente cliente2 = new Cliente("João", "joao@email.com", "123456", LocalDate.of(1990, 5, 10));

        assertNotEquals(cliente1, cliente2); // ids UUID diferentes
        assertNotEquals(cliente1.hashCode(), cliente2.hashCode());
    }
}
