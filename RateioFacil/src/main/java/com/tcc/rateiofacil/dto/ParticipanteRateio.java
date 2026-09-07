package com.tcc.rateiofacil.dto;

import java.math.BigDecimal;

public class ParticipanteRateio {

    private Long usuarioId;
    private String nome;
    private BigDecimal total;

    public ParticipanteRateio(Long usuarioId, String nome, BigDecimal total) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.total = total;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}