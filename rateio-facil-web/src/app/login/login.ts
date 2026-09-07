import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';
import { RateioStateService } from '../rateio-state.service';
import { Usuario } from '../interfaces';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class LoginComponent {
  private readonly api = inject(ApiService);
  private readonly state = inject(RateioStateService);
  private readonly router = inject(Router);

  modoCadastro = false;
  carregando = false;
  erro = '';

  loginForm = { email: '', senha: '' };
  cadastroForm = { nome: '', email: '', senha: '', confirmacao: '' };

  entrar() {
    this.erro = '';
    if (!this.loginForm.email || !this.loginForm.senha) {
      this.erro = 'Preencha email e senha.';
      return;
    }
    this.carregando = true;
    this.api.login(this.loginForm).subscribe({
      next: (usuario) => this.logar(usuario),
      error: (err) => {
        this.carregando = false;
        this.erro = this.mensagemErro(err);
      },
    });
  }

  criar() {
    this.erro = '';
    const { nome, email, senha, confirmacao } = this.cadastroForm;
    if (!nome || !email || !senha) {
      this.erro = 'Preencha nome, email e senha.';
      return;
    }
    if (senha !== confirmacao) {
      this.erro = 'As senhas não conferem.';
      return;
    }
    this.carregando = true;
    this.api.criarUsuario({ nome, email, senha }).subscribe({
      next: (usuario) => this.logar(usuario),
      error: (err) => {
        this.carregando = false;
        this.erro = this.mensagemErro(err);
      },
    });
  }

  alterarModo() {
    this.modoCadastro = !this.modoCadastro;
    this.erro = '';
  }

  private logar(usuario: Usuario) {
    this.state.usuario.set(usuario);
    this.carregando = false;
    this.router.navigate(['/participantes']);
  }

  private mensagemErro(err: unknown): string {
    if (err && typeof err === 'object' && 'error' in err) {
      const body = (err as { error?: { message?: string } | string }).error;
      if (typeof body === 'string') {
        return body;
      }
      if (body && typeof body === 'object' && body.message) {
        return body.message;
      }
    }
    return 'Erro inesperado. Verifique o backend.';
  }
}