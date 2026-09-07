package com.tcc.rateiofacil.dto;

import com.tcc.rateiofacil.model.Conta;
import com.tcc.rateiofacil.model.Item;

import java.time.LocalDateTime;
import java.util.List;

public class ContaResponse {

    private Long id;
    private String descricao;
    private LocalDateTime criadaEm;
    private UsuarioResponse dono;
    private List<ItemResponse> itens;

    public static ContaResponse from(Conta conta) {
        ContaResponse response = new ContaResponse();
        response.id = conta.getId();
        response.descricao = conta.getDescricao();
        response.criadaEm = conta.getCriadaEm();
        response.dono = UsuarioResponse.from(conta.getDono());
        response.itens = conta.getItens().stream().map(ItemResponse::from).toList();
        return response;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getCriadaEm() {
        return criadaEm;
    }

    public UsuarioResponse getDono() {
        return dono;
    }

    public List<ItemResponse> getItens() {
        return itens;
    }
}