package com.tcc.rateiofacil.controller;

import com.tcc.rateiofacil.dto.ContaRequest;
import com.tcc.rateiofacil.dto.ContaResponse;
import com.tcc.rateiofacil.dto.DivisaoResponse;
import com.tcc.rateiofacil.dto.HistoricoResponse;
import com.tcc.rateiofacil.dto.ItemRequest;
import com.tcc.rateiofacil.service.ContaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaService service;

    public ContaController(ContaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContaResponse criar(@RequestBody ContaRequest request) {
        return service.criarConta(request);
    }

    @GetMapping("/{contaId}")
    public ContaResponse buscar(@PathVariable Long contaId) {
        return service.buscar(contaId);
    }

    @PostMapping("/{contaId}/itens")
    public ContaResponse adicionarItem(@PathVariable Long contaId, @RequestBody ItemRequest request) {
        return service.adicionarItem(contaId, request);
    }

    @GetMapping("/{contaId}/divisao")
    public DivisaoResponse calcularDivisao(@PathVariable Long contaId) {
        return service.calcularDivisao(contaId);
    }

    @GetMapping("/historico/{participanteId}")
    public List<HistoricoResponse> historicoPorParticipante(@PathVariable Long participanteId) {
        return service.historicoPorParticipante(participanteId);
    }
}