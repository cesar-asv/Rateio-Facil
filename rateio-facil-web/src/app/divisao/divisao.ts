import { Component, OnInit, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';
import { RateioStateService } from '../rateio-state.service';
import { Divisao } from '../interfaces';

@Component({
  selector: 'app-divisao',
  imports: [],
  templateUrl: './divisao.html',
  styleUrl: './divisao.css',
})
export class DivisaoComponent implements OnInit {
  private readonly api = inject(ApiService);
  private readonly state = inject(RateioStateService);
  private readonly router = inject(Router);

  readonly carregando = signal(false);
  readonly erro = signal('');
  readonly divisao = signal<Divisao | null>(null);

  ngOnInit(): void {
    this.carregar();
  }

  carregar() {
    const conta = this.state.conta();
    if (!conta) {
      this.router.navigate(['/menu']);
      return;
    }
    this.carregando.set(true);
    this.erro.set('');
    this.api.calcularDivisao(conta.id).subscribe({
      next: (divisao) => {
        this.divisao.set(divisao);
        this.carregando.set(false);
      },
      error: () => {
        this.carregando.set(false);
        this.erro.set('Não foi possível calcular a divisão.');
      },
    });
  }

  novaConta() {
    this.state.reiniciar();
    this.router.navigate(['/participantes']);
  }

  formatarMoeda(valor: number): string {
    return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }
}
