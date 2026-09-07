package com.tcc.rateiofacil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private Integer quantidade = 1;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @ManyToMany
    @JoinTable(name = "item_participante",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> participantes = new HashSet<>();

    public Item() {
    }

    public Item(String descricao, BigDecimal valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Set<Usuario> getParticipantes() {
        return participantes;
    }

    public void adicionarParticipante(Usuario usuario) {
        participantes.add(usuario);
    }

    public BigDecimal getValorTotal() {
        return valor.multiply(BigDecimal.valueOf(quantidade));
    }
}