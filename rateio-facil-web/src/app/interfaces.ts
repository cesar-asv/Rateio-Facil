export interface Usuario {
  id: number;
  nome: string;
  email: string;
}

export interface Participante {
  id: number;
  nome: string;
  email: string;
}

export interface Item {
  id: number;
  descricao: string;
  valor: number;
  quantidade: number;
  valorTotal: number;
  participantes: Participante[];
}

export interface Conta {
  id: number;
  descricao: string;
  criadaEm: string;
  dono: Usuario;
  itens: Item[];
}

export interface Divisao {
  contaId: number;
  total: number;
  rateio: ParticipanteRateio[];
}

export interface ParticipanteRateio {
  participanteId: number;
  nome: string;
  total: number;
}

export interface ItemPayload {
  descricao: string;
  valor: number;
  quantidade: number;
  participantesIds: number[];
}

export interface HistoricoRateioItem {
  participanteNome: string;
  valor: number;
}

export interface HistoricoItem {
  descricao: string;
  valorTotal: number;
  rateio: HistoricoRateioItem[];
}

export interface Historico {
  contaId: number;
  descricao: string;
  criadaEm: string;
  itens: HistoricoItem[];
  total: number;
  totalPago: number;
}