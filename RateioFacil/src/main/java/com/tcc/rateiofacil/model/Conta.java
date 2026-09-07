package com.tcc.rateiofacil.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime criadaEm;

    @ManyToOne
    @JoinColumn(name = "dono_id", nullable = false)
    private Usuario dono;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itens = new ArrayList<>();

    public Conta() {
    }

    public Conta(String descricao, Usuario dono) {
        this.descricao = descricao;
        this.dono = dono;
        this.criadaEm = LocalDateTime.now();
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

    public LocalDateTime getCriadaEm() {
        return criadaEm;
    }

    public void setCriadaEm(LocalDateTime criadaEm) {
        this.criadaEm = criadaEm;
    }

    public Usuario getDono() {
        return dono;
    }

    public void setDono(Usuario dono) {
        this.dono = dono;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void adicionarItem(Item item) {
        itens.add(item);
        item.setConta(this);
    }
}