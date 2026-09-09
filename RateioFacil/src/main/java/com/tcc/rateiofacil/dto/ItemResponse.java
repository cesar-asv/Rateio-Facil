package com.tcc.rateiofacil.dto;

import com.tcc.rateiofacil.model.Item;

import java.math.BigDecimal;
import java.util.List;

public class ItemResponse {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private Integer quantidade;
    private BigDecimal valorTotal;
    private List<ParticipanteResponse> participantes;

    public static ItemResponse from(Item item) {
        ItemResponse response = new ItemResponse();
        response.id = item.getId();
        response.descricao = item.getDescricao();
        response.valor = item.getValor();
        response.quantidade = item.getQuantidade();
        response.valorTotal = item.getValorTotal();
        response.participantes = item.getParticipantes().stream()
                .map(ParticipanteResponse::from)
                .toList();
        return response;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public List<ParticipanteResponse> getParticipantes() {
        return participantes;
    }
}