package com.clientes.repository;

import com.clientes.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void testSaveAndFindByEmail() {
        Cliente cliente = new Cliente("Maria", "maria@email.com", "senha123", LocalDate.of(1985, 3, 15));
        clienteRepository.save(cliente);

        Optional<Cliente> encontrado = clienteRepository.findByEmail("maria@email.com");
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("Maria");
    }

    @Test
    void testExistsByEmail() {
        Cliente cliente = new Cliente("Carlos", "carlos@email.com", "senha123", LocalDate.of(1992, 8, 20));
        clienteRepository.save(cliente);

        boolean existe = clienteRepository.existsByEmail("carlos@email.com");
        assertThat(existe).isTrue();
    }
}
