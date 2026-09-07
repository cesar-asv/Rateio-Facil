export interface Usuario {
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
  participantes: Usuario[];
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
  usuarioId: number;
  nome: string;
  total: number;
}

export interface ItemPayload {
  descricao: string;
  valor: number;
  quantidade: number;
  participantesIds: number[];
}