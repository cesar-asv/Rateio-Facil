package com.tcc.rateiofacil.service;

import com.tcc.rateiofacil.dto.ContaRequest;
import com.tcc.rateiofacil.dto.ContaResponse;
import com.tcc.rateiofacil.dto.DivisaoResponse;
import com.tcc.rateiofacil.dto.ItemRequest;
import com.tcc.rateiofacil.dto.ParticipanteRateio;
import com.tcc.rateiofacil.model.Conta;
import com.tcc.rateiofacil.model.Item;
import com.tcc.rateiofacil.model.Usuario;
import com.tcc.rateiofacil.repository.ContaRepository;
import com.tcc.rateiofacil.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;

    public ContaService(ContaRepository contaRepository, UsuarioRepository usuarioRepository) {
        this.contaRepository = contaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ContaResponse criarConta(ContaRequest request) {
        Usuario dono = usuarioRepository.findById(request.getDonoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        Conta conta = new Conta(request.getDescricao(), dono);
        return ContaResponse.from(contaRepository.save(conta));
    }

    public ContaResponse buscar(Long contaId) {
        return ContaResponse.from(buscarConta(contaId));
    }

    public ContaResponse adicionarItem(Long contaId, ItemRequest request) {
        Conta conta = buscarConta(contaId);
        Item item = new Item(request.getDescricao(), request.getValor());
        if (request.getQuantidade() != null) {
            item.setQuantidade(request.getQuantidade());
        }
        for (Long participanteId : request.getParticipantesIds()) {
            Usuario usuario = usuarioRepository.findById(participanteId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Usuário não encontrado"));
            item.adicionarParticipante(usuario);
        }
        conta.adicionarItem(item);
        return ContaResponse.from(contaRepository.save(conta));
    }

    public DivisaoResponse calcularDivisao(Long contaId) {
        Conta conta = buscarConta(contaId);
        Map<Long, ParticipanteRateio> porUsuario = new LinkedHashMap<>(); // lista de DTOs do rateio: usuario-valorPorUsuario
        BigDecimal total = BigDecimal.ZERO;

        for (Item item : conta.getItens()) {
            BigDecimal valorItem = item.getValorTotal();
            int n = item.getParticipantes().size();
            if (n == 0) { // TODO: implementar tratamento de erro pra item sem participantes
                continue;
            }

            BigDecimal valorPorPessoa = valorItem.divide(BigDecimal.valueOf(n), 2, RoundingMode.HALF_UP);

            // para calcular o resto da divisao de itens
            BigDecimal somaDasPartes = valorPorPessoa.multiply(BigDecimal.valueOf(n));
            BigDecimal diferenca = valorItem.subtract(somaDasPartes);

            List<Usuario> participantes = List.copyOf(item.getParticipantes());
            for (int i = 0; i < n; i++) {
                Usuario usuario = participantes.get(i);
                BigDecimal parte = valorPorPessoa;
                if (i == n - 1) {
                    parte = parte.add(diferenca); // o resto da divisão vai para o ultimo participante
                }
                ParticipanteRateio rateio = porUsuario.get(usuario.getId());
                if (rateio == null) {
                    rateio = new ParticipanteRateio(usuario.getId(), usuario.getNome(), BigDecimal.ZERO);
                    porUsuario.put(usuario.getId(), rateio);
                }
                rateio.setTotal(rateio.getTotal().add(parte));
            }
            total = total.add(valorItem);
        }

        return new DivisaoResponse(conta.getId(), total, List.copyOf(porUsuario.values()));
    }

    private Conta buscarConta(Long contaId) {
        return contaRepository.findById(contaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));
    }
}