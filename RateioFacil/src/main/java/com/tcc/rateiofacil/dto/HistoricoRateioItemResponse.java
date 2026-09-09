package com.tcc.rateiofacil.dto;

import java.math.BigDecimal;

public class HistoricoRateioItemResponse {

    private String participanteNome;
    private BigDecimal valor;

    public HistoricoRateioItemResponse(String participanteNome, BigDecimal valor) {
        this.participanteNome = participanteNome;
        this.valor = valor;
    }

    public String getParticipanteNome() {
        return participanteNome;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
