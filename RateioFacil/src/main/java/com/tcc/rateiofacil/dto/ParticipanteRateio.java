package com.tcc.rateiofacil.dto;

import java.math.BigDecimal;

public class ParticipanteRateio {

    private Long participanteId;
    private String nome;
    private BigDecimal total;

    public ParticipanteRateio(Long participanteId, String nome, BigDecimal total) {
        this.participanteId = participanteId;
        this.nome = nome;
        this.total = total;
    }

    public Long getParticipanteId() {
        return participanteId;
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