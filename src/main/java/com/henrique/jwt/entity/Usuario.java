package com.henrique.jwt.entity;

import java.util.List;

/**
 * Created by henrique on 19/02/17.
 */
public class Usuario {

    private Long idUsuario;

    private String nome;

    private int idade;

    private String email;

    private String senha;

    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
