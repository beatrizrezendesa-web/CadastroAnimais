package br.com.projeto.cadastro_animais.model;

import br.com.projeto.cadastro_animais.validation.CpfValido;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
public class Adotante {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String nome;
    @NotBlank @CpfValido
    private String cpf;
    private String telefone;
    @Email private String email;
    public Adotante() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
