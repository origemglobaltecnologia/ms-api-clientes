package com.clientes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "Nome não pode ser nulo")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "Email não pode ser nulo")
    @Email(message = "Email inválido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Senha não pode ser nula")
    @Size(min = 6, message = "Senha deve ter ao menos 6 caracteres")
    @Column(nullable = false)
    private String senha;

    @NotNull(message = "Data de nascimento não pode ser nula")
    @Column(nullable = false)
    private LocalDate dataNascimento; // Campo adicionado e usado nos testes

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCadastro = LocalDateTime.now();

    // Construtor vazio (necessário pelo JPA/Hibernate)
    public Cliente() {}

    /**
     * Construtor completo com todos os campos (exceto ID e dataCadastro que são gerados).
     * Este construtor é importante para facilitar a criação de objetos em testes.
     */
    public Cliente(String nome, String email, String senha, LocalDate dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
    }

    // Opcional: construtor completo incluindo o ID para uso em testes de retorno.
    public Cliente(UUID id, String nome, String email, String senha, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
    }


    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    // Métodos essenciais corrigidos:
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }


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

