import { Injectable, signal } from '@angular/core';
import { Usuario, Participante, Conta } from './interfaces';

@Injectable({ providedIn: 'root' })
export class RateioStateService {
  readonly usuario = signal<Usuario | null>(null);
  readonly participantes = signal<Participante[]>([]);
  readonly conta = signal<Conta | null>(null);

  reiniciar() {
    this.participantes.set([]);
    this.conta.set(null);
  }

  sair() {
    this.usuario.set(null);
    this.participantes.set([]);
    this.conta.set(null);
  }
}