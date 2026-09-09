import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { RateioStateService } from '../rateio-state.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.html',
  styleUrl: './menu.css',
})
export class MenuComponent {
  private readonly state = inject(RateioStateService);
  private readonly router = inject(Router);

  get usuarioNome(): string {
    return this.state.usuario()?.nome ?? '';
  }

  novoRateio() {
    this.state.reiniciar();
    this.router.navigate(['/participantes']);
  }

  historico() {
    this.router.navigate(['/historico']);
  }

  sair() {
    this.state.sair();
    this.router.navigate(['/']);
  }
}
