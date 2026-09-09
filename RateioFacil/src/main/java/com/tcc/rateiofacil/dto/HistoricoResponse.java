package com.tcc.rateiofacil.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class HistoricoResponse {

    private Long contaId;
    private String descricao;
    private LocalDateTime criadaEm;
    private List<HistoricoItemResponse> itens;
    private BigDecimal total;
    private BigDecimal totalPago;

    public HistoricoResponse(Long contaId, String descricao, LocalDateTime criadaEm,
                             List<HistoricoItemResponse> itens, BigDecimal total, BigDecimal totalPago) {
        this.contaId = contaId;
        this.descricao = descricao;
        this.criadaEm = criadaEm;
        this.itens = itens;
        this.total = total;
        this.totalPago = totalPago;
    }

    public Long getContaId() {
        return contaId;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getCriadaEm() {
        return criadaEm;
    }

    public List<HistoricoItemResponse> getItens() {
        return itens;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getTotalPago() {
        return totalPago;
    }
}
