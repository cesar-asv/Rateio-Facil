package com.tcc.rateiofacil.service;

import com.tcc.rateiofacil.dto.ParticipanteRequest;
import com.tcc.rateiofacil.dto.ParticipanteResponse;
import com.tcc.rateiofacil.model.Participante;
import com.tcc.rateiofacil.repository.ParticipanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;

    public ParticipanteService(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    public ParticipanteResponse criar(ParticipanteRequest request) {
        Participante participante = new Participante(request.getNome(), request.getEmail());
        return ParticipanteResponse.from(participanteRepository.save(participante));
    }

    public List<ParticipanteResponse> listar() {
        return participanteRepository.findAll().stream()
                .map(ParticipanteResponse::from)
                .toList();
    }

    public List<ParticipanteResponse> buscar(String nome, String email) {
        return participanteRepository.buscar(nome, email).stream()
                .map(ParticipanteResponse::from)
                .toList();
    }
}
