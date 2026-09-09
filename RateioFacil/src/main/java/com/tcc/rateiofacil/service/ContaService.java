package com.tcc.rateiofacil.service;

import com.tcc.rateiofacil.dto.ContaRequest;
import com.tcc.rateiofacil.dto.ContaResponse;
import com.tcc.rateiofacil.dto.DivisaoResponse;
import com.tcc.rateiofacil.dto.HistoricoItemResponse;
import com.tcc.rateiofacil.dto.HistoricoRateioItemResponse;
import com.tcc.rateiofacil.dto.HistoricoResponse;
import com.tcc.rateiofacil.dto.ItemRequest;
import com.tcc.rateiofacil.dto.ParticipanteRateio;
import com.tcc.rateiofacil.model.Conta;
import com.tcc.rateiofacil.model.Item;
import com.tcc.rateiofacil.model.Participante;
import com.tcc.rateiofacil.model.Usuario;
import com.tcc.rateiofacil.repository.ContaRepository;
import com.tcc.rateiofacil.repository.ItemRepository;
import com.tcc.rateiofacil.repository.ParticipanteRepository;
import com.tcc.rateiofacil.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ParticipanteRepository participanteRepository;
    private final ItemRepository itemRepository;

    public ContaService(ContaRepository contaRepository, UsuarioRepository usuarioRepository,
                        ParticipanteRepository participanteRepository, ItemRepository itemRepository) {
        this.contaRepository = contaRepository;
        this.usuarioRepository = usuarioRepository;
        this.participanteRepository = participanteRepository;
        this.itemRepository = itemRepository;
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
            Participante participante = participanteRepository.findById(participanteId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Participante não encontrado"));
            item.adicionarParticipante(participante);
        }
        conta.adicionarItem(item);
        return ContaResponse.from(contaRepository.save(conta));
    }

    public DivisaoResponse calcularDivisao(Long contaId) {
        Conta conta = buscarConta(contaId);
        // lista de DTOs do rateio: participante-valorPorParticipante
        Map<Long, ParticipanteRateio> porParticipante = new LinkedHashMap<>();
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

            List<Participante> participantes = List.copyOf(item.getParticipantes());
            for (int i = 0; i < n; i++) {
                Participante participante = participantes.get(i);
                BigDecimal parte = valorPorPessoa;
                if (i == n - 1) {
                    parte = parte.add(diferenca); // o resto da divisão vai para o ultimo participante
                }
                ParticipanteRateio rateio = porParticipante.get(participante.getId());
                if (rateio == null) {
                    rateio = new ParticipanteRateio(participante.getId(), participante.getNome(), BigDecimal.ZERO);
                    porParticipante.put(participante.getId(), rateio);
                }
                rateio.setTotal(rateio.getTotal().add(parte));
            }
            total = total.add(valorItem);
        }

        return new DivisaoResponse(conta.getId(), total, List.copyOf(porParticipante.values()));
    }

    public List<HistoricoResponse> historicoPorParticipante(Long participanteId) {
        Participante participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Participante não encontrado"));

        List<Item> itens = itemRepository.findByParticipante(participante);
        Map<Conta, List<Item>> itensPorConta = new LinkedHashMap<>();
        for (Item item : itens) {
            itensPorConta.computeIfAbsent(item.getConta(), k -> new ArrayList<>()).add(item);
        }

        List<HistoricoResponse> resultado = new ArrayList<>();
        for (Map.Entry<Conta, List<Item>> entry : itensPorConta.entrySet()) {
            Conta conta = entry.getKey();
            List<HistoricoItemResponse> itensHistorico = new ArrayList<>();
            BigDecimal totalConta = BigDecimal.ZERO;
            BigDecimal totalPago = BigDecimal.ZERO;

            for (Item item : entry.getValue()) {
                BigDecimal valorItem = item.getValorTotal();
                int n = item.getParticipantes().size();
                BigDecimal valorPorPessoa = n > 0
                        ? valorItem.divide(BigDecimal.valueOf(n), 2, RoundingMode.HALF_UP)
                        : BigDecimal.ZERO;

                List<HistoricoRateioItemResponse> rateioItem = item.getParticipantes().stream()
                        .map(p -> new HistoricoRateioItemResponse(p.getNome(), valorPorPessoa))
                        .toList();

                itensHistorico.add(new HistoricoItemResponse(item.getDescricao(), valorItem, rateioItem));
                totalConta = totalConta.add(valorItem);
                totalPago = totalPago.add(valorPorPessoa);
            }

            resultado.add(new HistoricoResponse(
                    conta.getId(), conta.getDescricao(), conta.getCriadaEm(),
                    itensHistorico, totalConta, totalPago));
        }

        return resultado;
    }

    private Conta buscarConta(Long contaId) {
        return contaRepository.findById(contaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));
    }
}