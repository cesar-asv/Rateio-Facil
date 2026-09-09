package com.tcc.rateiofacil.dto;

import com.tcc.rateiofacil.model.Participante;

public class ParticipanteResponse {

    private Long id;
    private String nome;
    private String email;

    public static ParticipanteResponse from(Participante participante) {
        ParticipanteResponse response = new ParticipanteResponse();
        response.id = participante.getId();
        response.nome = participante.getNome();
        response.email = participante.getEmail();
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
