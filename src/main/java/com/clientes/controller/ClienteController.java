package com.clientes.controller;

import com.clientes.model.Cliente;
import com.clientes.repository.ClienteRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*") // Permite acesso de qualquer origem
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // CREATE (Cria um novo cliente)
    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        // Profissional: Adicionar verificação de unicidade do login antes de salvar
        if (clienteRepository.existsByLogin(cliente.getLogin())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
        }
        
        // Em um sistema real, a senha deveria ser codificada aqui antes de salvar!
        
        Cliente novo = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    // READ (Lista todos os clientes)
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // READ (Busca por ID - UUID)
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable UUID id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        
        // Usa o método 'map' do Optional para retornar o cliente ou 404
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    // READ (Busca por Login, um método mais realista para login/detalhes)
    @GetMapping("/login/{login}")
    public ResponseEntity<Cliente> buscarClientePorLogin(@PathVariable String login) {
        Optional<Cliente> cliente = clienteRepository.findByLogin(login);
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // UPDATE (Atualiza um cliente existente)
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable UUID id, @RequestBody Cliente clienteAtualizado) {
        
        return clienteRepository.findById(id).map(clienteExistente -> {
            
            // 1. Atualiza apenas os campos permitidos
            clienteExistente.setNome(clienteAtualizado.getNome());
            clienteExistente.setLogin(clienteAtualizado.getLogin());
            
            // 2. A senha é atualizada apenas se uma nova for fornecida
            if (clienteAtualizado.getSenha() != null && !clienteAtualizado.getSenha().isEmpty()) {
                // Em um sistema real, a senha precisa ser codificada aqui
                clienteExistente.setSenha(clienteAtualizado.getSenha());
            }

            Cliente atualizado = clienteRepository.save(clienteExistente);
            return ResponseEntity.ok(atualizado);
            
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // DELETE (Exclui um cliente)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable UUID id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            // Retorna 204 No Content para exclusão bem-sucedida
            return ResponseEntity.noContent().build(); 
        }
        // Retorna 404 Not Found se o ID não existir
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

