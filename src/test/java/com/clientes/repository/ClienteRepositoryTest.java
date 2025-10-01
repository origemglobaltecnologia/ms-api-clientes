package com.clientes.repository;

import com.clientes.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void testFindByEmail() {
        String email = "teste@repo.com";
        // CORREÇÃO: Usando o construtor correto de 3 argumentos
        Cliente cliente = new Cliente("Repo Test", email, "senha123");
        clienteRepository.save(cliente);

        Optional<Cliente> found = clienteRepository.findByEmail(email);

        assertTrue(found.isPresent());
        assertEquals(email, found.get().getEmail());
    }

    @Test
    void testExistsByEmail() {
        String email = "exists@repo.com";
        // CORREÇÃO: Usando o construtor correto de 3 argumentos
        Cliente cliente = new Cliente("Exist Test", email, "senha123");
        clienteRepository.save(cliente);

        boolean exists = clienteRepository.existsByEmail(email);
        boolean notExists = clienteRepository.existsByEmail("naoexiste@repo.com");

        assertTrue(exists);
        assertFalse(notExists);
    }
}

