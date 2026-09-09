import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';
import { Participante, Historico } from '../interfaces';

@Component({
  selector: 'app-historico',
  imports: [FormsModule],
  templateUrl: './historico.html',
  styleUrl: './historico.css',
})
export class HistoricoComponent {
  private readonly api = inject(ApiService);
  private readonly router = inject(Router);

  buscaNome = '';
  buscaEmail = '';
  participantes = signal<Participante[]>([]);
  participanteSelecionado = signal<Participante | null>(null);
  historico = signal<Historico[]>([]);
  carregando = signal(false);
  erro = signal('');
  buscaRealizada = signal(false);

  buscar() {
    this.erro.set('');
    this.participanteSelecionado.set(null);
    this.historico.set([]);
    this.carregando.set(true);
    this.buscaRealizada.set(true);
    this.api.buscarParticipantes(this.buscaNome, this.buscaEmail).subscribe({
      next: (lista) => {
        this.participantes.set(lista);
        this.carregando.set(false);
      },
      error: () => {
        this.participantes.set([]);
        this.carregando.set(false);
        this.erro.set('Não foi possível buscar participantes.');
      },
    });
  }

  selecionar(participante: Participante) {
    this.participanteSelecionado.set(participante);
    this.carregando.set(true);
    this.erro.set('');
    this.api.historicoPorParticipante(participante.id).subscribe({
      next: (hist) => {
        this.historico.set(hist);
        this.carregando.set(false);
      },
      error: () => {
        this.carregando.set(false);
        this.erro.set('Não foi possível carregar o histórico.');
      },
    });
  }

  voltar() {
    this.participanteSelecionado.set(null);
    this.historico.set([]);
  }

  voltarMenu() {
    this.router.navigate(['/menu']);
  }

  formatarMoeda(valor: number): string {
    return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }

  formatarData(data: string): string {
    return new Date(data).toLocaleDateString('pt-BR');
  }
}
