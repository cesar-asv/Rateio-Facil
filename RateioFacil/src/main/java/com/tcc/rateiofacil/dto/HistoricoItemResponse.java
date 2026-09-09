package com.tcc.rateiofacil.dto;

import java.math.BigDecimal;
import java.util.List;

public class HistoricoItemResponse {

    private String descricao;
    private BigDecimal valorTotal;
    private List<HistoricoRateioItemResponse> rateio;

    public HistoricoItemResponse(String descricao, BigDecimal valorTotal,
                                 List<HistoricoRateioItemResponse> rateio) {
        this.descricao = descricao;
        this.valorTotal = valorTotal;
        this.rateio = rateio;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public List<HistoricoRateioItemResponse> getRateio() {
        return rateio;
    }
}
