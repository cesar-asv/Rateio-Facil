package com.tcc.rateiofacil.dto;

import com.tcc.rateiofacil.model.Item;
import com.tcc.rateiofacil.model.Usuario;

import java.math.BigDecimal;
import java.util.List;

public class ItemResponse {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private Integer quantidade;
    private BigDecimal valorTotal;
    private List<UsuarioResponse> participantes;

    public static ItemResponse from(Item item) {
        ItemResponse response = new ItemResponse();
        response.id = item.getId();
        response.descricao = item.getDescricao();
        response.valor = item.getValor();
        response.quantidade = item.getQuantidade();
        response.valorTotal = item.getValorTotal();
        response.participantes = item.getParticipantes().stream()
                .map(UsuarioResponse::from)
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

    public List<UsuarioResponse> getParticipantes() {
        return participantes;
    }
}