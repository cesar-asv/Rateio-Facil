package com.tcc.rateiofacil.dto;

import java.math.BigDecimal;
import java.util.List;

public class DivisaoResponse {

    private Long contaId;
    private BigDecimal total;
    private List<ParticipanteRateio> rateio;

    public DivisaoResponse(Long contaId, BigDecimal total, List<ParticipanteRateio> rateio) {
        this.contaId = contaId;
        this.total = total;
        this.rateio = rateio;
    }

    public Long getContaId() {
        return contaId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<ParticipanteRateio> getRateio() {
        return rateio;
    }
}