package com.clientes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Objects;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "Nome não pode ser nulo")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "Login não pode ser nulo")
    @Column(nullable = false, unique = true)
    private String login; // O login será o campo único para acesso

    @NotNull(message = "Senha não pode ser nula")
    @Column(nullable = false)
    // Nota: Em um sistema real, use @JsonIgnore (da Jackson) para não expor a senha em serializações.
    private String senha; 

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCadastro = LocalDateTime.now();

    public Cliente() {}

    public Cliente(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
    // O setter não é fornecido para dataCadastro, pois é inicializado no construtor/campo

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

