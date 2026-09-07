package com.tcc.rateiofacil.dto;

import com.tcc.rateiofacil.model.Usuario;

public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;

    public static UsuarioResponse from(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.id = usuario.getId();
        response.nome = usuario.getNome();
        response.email = usuario.getEmail();
        return response;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}