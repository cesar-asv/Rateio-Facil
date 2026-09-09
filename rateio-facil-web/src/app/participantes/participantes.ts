import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';
import { RateioStateService } from '../rateio-state.service';
import { Participante } from '../interfaces';

@Component({
  selector: 'app-participantes',
  imports: [CommonModule, FormsModule],
  templateUrl: './participantes.html',
  styleUrl: './participantes.css',
})
export class ParticipantesComponent implements OnInit {
  private readonly api = inject(ApiService);
  private readonly state = inject(RateioStateService);
  private readonly router = inject(Router);

  participantes: Participante[] = [];
  carregando = false;
  erro = '';
  descricao = '';
  selecionados: Set<number> = new Set();

  novoNome = '';
  novoEmail = '';

  ngOnInit(): void {
    this.carregarParticipantes();
  }

  carregarParticipantes() {
    this.carregando = true;
    this.api.listarParticipantes().subscribe({
      next: (participantes) => {
        this.participantes = participantes;
        this.carregando = false;
      },
      error: (err) => {
        this.carregando = false;
        this.erro = 'Não foi possível listar os participantes.';
      },
    });
  }

  cadastrarParticipante() {
    this.erro = '';
    if (!this.novoNome.trim() || !this.novoEmail.trim()) {
      this.erro = 'Informe nome e email do participante.';
      return;
    }
    this.carregando = true;
    this.api
      .criarParticipante({ nome: this.novoNome.trim(), email: this.novoEmail.trim() })
      .subscribe({
        next: (participante) => {
          this.participantes = [...this.participantes, participante];
          this.novoNome = '';
          this.novoEmail = '';
          this.carregando = false;
        },
        error: (err) => {
          this.carregando = false;
          this.erro = 'Não foi possível cadastrar o participante.';
        },
      });
  }

  alternar(participante: Participante) {
    if (this.selecionados.has(participante.id)) {
      this.selecionados.delete(participante.id);
    } else {
      this.selecionados.add(participante.id);
    }
  }

  continuar() {
    this.erro = '';
    if (!this.descricao.trim()) {
      this.erro = 'Informe uma descrição para a conta.';
      return;
    }
    if (this.selecionados.size === 0) {
      this.erro = 'Selecione ao menos um participante.';
      return;
    }
    const selecionados = this.participantes.filter((p) => this.selecionados.has(p.id));
    this.state.participantes.set(selecionados);
    const dono = this.state.usuario();
    if (!dono) {
      console.log('if !dono');
      this.router.navigate(['/menu']);
      return;
    }
    this.carregando = true;
    this.api.criarConta(this.descricao.trim(), dono.id).subscribe({
      next: (conta) => {
        this.state.conta.set(conta);
        this.carregando = false;
        this.router.navigate(['/itens']);
      },
      error: (err) => {
        this.carregando = false;
        this.erro = 'Não foi possível criar a conta.';
      },
    });
  }
}
