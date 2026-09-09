import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario, Participante, Conta, Divisao, ItemPayload, Historico } from './interfaces';

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

  listarParticipantes(): Observable<Participante[]> {
    return this.http.get<Participante[]>('/api/participantes');
  }

  criarParticipante(dados: { nome: string; email: string }): Observable<Participante> {
    return this.http.post<Participante>('/api/participantes', dados);
  }

  buscarParticipantes(nome: string, email: string): Observable<Participante[]> {
    let params = new HttpParams();
    if (nome) params = params.set('nome', nome);
    if (email) params = params.set('email', email);
    return this.http.get<Participante[]>('/api/participantes/buscar', { params });
  }

  historicoPorParticipante(participanteId: number): Observable<Historico[]> {
    return this.http.get<Historico[]>(`/api/contas/historico/${participanteId}`);
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