package com.clientes.service;

import com.clientes.model.Cliente;
import com.clientes.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
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

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente("João", "joao@email.com", "123456", LocalDate.of(1990, 5, 10));
    }

    @Test
    void testSalvarClienteComSenhaCriptografada() {
        when(passwordEncoder.encode("123456")).thenReturn("encoded123");
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

        Cliente salvo = clienteService.salvar(cliente);

        assertNotNull(salvo);
        assertEquals("João", salvo.getNome());
        assertEquals("encoded123", salvo.getSenha());

        verify(passwordEncoder, times(1)).encode("123456");
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testListarTodos() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));

        List<Cliente> clientes = clienteService.listarTodos();

        assertEquals(1, clientes.size());
        assertEquals("João", clientes.get(0).getNome());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        UUID id = cliente.getId();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        Optional<Cliente> encontrado = clienteService.buscarPorId(id);

        assertTrue(encontrado.isPresent());
        assertEquals("João", encontrado.get().getNome());
        verify(clienteRepository, times(1)).findById(id);
    }

    @Test
    void testBuscarPorEmail() {
        when(clienteRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(cliente));

        Optional<Cliente> encontrado = clienteService.buscarPorEmail("joao@email.com");

        assertTrue(encontrado.isPresent());
        assertEquals("João", encontrado.get().getNome());
        verify(clienteRepository, times(1)).findByEmail("joao@email.com");
    }

    @Test
    void testExistsByEmail() {
        when(clienteRepository.existsByEmail("joao@email.com")).thenReturn(true);

        boolean existe = clienteService.existsByEmail("joao@email.com");

        assertTrue(existe);
        verify(clienteRepository, times(1)).existsByEmail("joao@email.com");
    }
}
