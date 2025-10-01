package com.clientes.model;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testSettersAndGetters() {
        Cliente cliente = new Cliente();
        UUID id = UUID.randomUUID();
        LocalDateTime dataCadastro = LocalDateTime.now();

        cliente.setId(id);
        cliente.setNome("Alice");
        cliente.setEmail("alice@test.com");
        cliente.setSenha("securepass");
        // setDataNascimento() foi removido

        assertEquals(id, cliente.getId());
        assertEquals("Alice", cliente.getNome());
        assertEquals("alice@test.com", cliente.getEmail());
        assertEquals("securepass", cliente.getSenha());
        assertNotNull(cliente.getDataCadastro());
        // getDataNascimento() foi removido
    }

    @Test
    void testEqualityAndHashCode() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        // CORREÇÃO: Usando o construtor correto de 4 argumentos (UUID, String, String, String)
        Cliente clienteA = new Cliente(id1, "Teste", "a@test.com", "pass");
        Cliente clienteB = new Cliente(id1, "Teste", "a@test.com", "pass");
        
        // CORREÇÃO: Usando o construtor correto de 4 argumentos
        Cliente clienteC = new Cliente(id2, "Outro", "c@test.com", "pass");

        assertEquals(clienteA, clienteB);
        assertEquals(clienteA.hashCode(), clienteB.hashCode());
        assertNotEquals(clienteA, clienteC);
        assertNotEquals(clienteA.hashCode(), clienteC.hashCode());
    }
}

