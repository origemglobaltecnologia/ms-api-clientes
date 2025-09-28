package com.clientes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate; // <-- NOVO IMPORT
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

    // --- NOVO CAMPO: dataNascimento (usado nos testes) ---
    @NotNull(message = "Data de nascimento não pode ser nula")
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCadastro = LocalDateTime.now();

    // Construtor vazio (necessário pelo JPA)
    public Cliente() {}

    // Construtor original de 3 argumentos
    public Cliente(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        // O campo dataNascimento precisa ser inicializado ou o teste falhará
        // Se este construtor não for usado nos testes que esperam 4 argumentos, pode ser mantido assim.
        // Vamos focar no construtor de 4 argumentos.
    }

    // --- NOVO CONSTRUTOR de 4 argumentos (Corrige o erro de "no suitable constructor found") ---
    public Cliente(String nome, String email, String senha, LocalDate dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento; // Inicializa o novo campo
        this.dataCadastro = LocalDateTime.now();
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

    // --- NOVOS MÉTODOS: Getter e Setter para dataNascimento (Corrigem o erro "cannot find symbol") ---
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    // Não é necessário um setter para dataCadastro se for gerado automaticamente

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

