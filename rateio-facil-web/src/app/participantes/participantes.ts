import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';
import { RateioStateService } from '../rateio-state.service';
import { Usuario } from '../interfaces';

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

  usuarios: Usuario[] = [];
  carregando = false;
  erro = '';
  descricao = '';
  selecionados: Set<number> = new Set();

  ngOnInit(): void {
    this.carregarUsuarios();
  }

  carregarUsuarios() {
    this.carregando = true;
    this.api.listarUsuarios().subscribe({
      next: (usuarios) => {
        this.usuarios = usuarios;
        this.carregando = false;
      },
      error: (err) => {
        this.carregando = false;
        this.erro = 'Não foi possível listar os usuários.';
      },
    });
  }

  alternar(usuario: Usuario) {
    if (this.selecionados.has(usuario.id)) {
      this.selecionados.delete(usuario.id);
    } else {
      this.selecionados.add(usuario.id);
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
    const participantes = this.usuarios.filter((u) => this.selecionados.has(u.id));
    this.state.participantes.set(participantes);
    const dono = this.state.usuario();
    if (!dono) {
      this.router.navigate(['/']);
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