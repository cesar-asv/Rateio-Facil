import { Injectable, signal } from '@angular/core';
import { Usuario, Conta } from './interfaces';

@Injectable({ providedIn: 'root' })
export class RateioStateService {
  readonly usuario = signal<Usuario | null>(null);
  readonly participantes = signal<Usuario[]>([]);
  readonly conta = signal<Conta | null>(null);

  reiniciar() {
    this.usuario.set(null);
    this.participantes.set([]);
    this.conta.set(null);
  }
}