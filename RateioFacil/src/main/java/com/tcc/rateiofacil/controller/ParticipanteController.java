package com.tcc.rateiofacil.controller;

import com.tcc.rateiofacil.dto.ParticipanteRequest;
import com.tcc.rateiofacil.dto.ParticipanteResponse;
import com.tcc.rateiofacil.service.ParticipanteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    public ParticipanteController(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    @PostMapping
    public ParticipanteResponse criar(@RequestBody ParticipanteRequest request) {
        return participanteService.criar(request);
    }

    @GetMapping
    public List<ParticipanteResponse> listar() {
        return participanteService.listar();
    }

    @GetMapping("/buscar")
    public List<ParticipanteResponse> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email) {
        return participanteService.buscar(nome, email);
    }
}
