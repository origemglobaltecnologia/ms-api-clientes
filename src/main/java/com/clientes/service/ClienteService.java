package com.clientes.service;

import com.clientes.model.Cliente;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteService {

    Cliente criarCliente(Cliente cliente);
    List<Cliente> listarClientes();
    Optional<Cliente> buscarPorId(UUID id);
    Optional<Cliente> buscarPorEmail(String email);
    Cliente atualizarCliente(UUID id, Cliente dadosAtualizados);
    void excluirCliente(UUID id);
}
