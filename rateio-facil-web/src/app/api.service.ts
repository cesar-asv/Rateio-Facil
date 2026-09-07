import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario, Conta, Divisao, ItemPayload } from './interfaces';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly http = inject(HttpClient);

  listarUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>('/api/usuarios');
  }

  criarUsuario(dados: { nome: string; email: string; senha: string }): Observable<Usuario> {
    return this.http.post<Usuario>('/api/usuarios', dados);
  }

  login(dados: { email: string; senha: string }): Observable<Usuario> {
    return this.http.post<Usuario>('/api/usuarios/login', dados);
  }

  criarConta(descricao: string, donoId: number): Observable<Conta> {
    return this.http.post<Conta>('/api/contas', { descricao, donoId });
  }

  adicionarItem(contaId: number, item: ItemPayload): Observable<Conta> {
    return this.http.post<Conta>(`/api/contas/${contaId}/itens`, item);
  }

  calcularDivisao(contaId: number): Observable<Divisao> {
    return this.http.get<Divisao>(`/api/contas/${contaId}/divisao`);
  }
}