import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';
import { RateioStateService } from '../rateio-state.service';
import { Conta } from '../interfaces';

@Component({
  selector: 'app-itens',
  imports: [CommonModule, FormsModule],
  templateUrl: './itens.html',
  styleUrl: './itens.css',
})
export class ItensComponent {
  private readonly api = inject(ApiService);
  private readonly state = inject(RateioStateService);
  private readonly router = inject(Router);

  carregando = false;
  erro = '';

  itemForm = { descricao: '', valor: 0, quantidade: 1 };
  selecaoItem: Set<number> = new Set();

  get conta(): Conta | null {
    return this.state.conta();
  }

  get participantes() {
    return this.state.participantes();
  }

  alternarParticipante(id: number) {
    if (this.selecaoItem.has(id)) {
      this.selecaoItem.delete(id);
    } else {
      this.selecaoItem.add(id);
    }
  }

  adicionarItem() {
    this.erro = '';
    const conta = this.conta;
    if (!conta) {
      this.router.navigate(['/menu']);
      return;
    }
    if (!this.itemForm.descricao.trim()) {
      this.erro = 'Informe a descrição do item.';
      return;
    }
    if (!this.itemForm.valor || this.itemForm.valor <= 0) {
      this.erro = 'Informe um valor maior que zero.';
      return;
    }
    if (!this.itemForm.quantidade || this.itemForm.quantidade < 1) {
      this.erro = 'Informe uma quantidade válida.';
      return;
    }
    if (this.selecaoItem.size === 0) {
      this.erro = 'Selecione ao menos um participante para o item.';
      return;
    }
    this.carregando = true;
    this.api
      .adicionarItem(conta.id, {
        descricao: this.itemForm.descricao.trim(),
        valor: this.itemForm.valor,
        quantidade: this.itemForm.quantidade,
        participantesIds: [...this.selecaoItem],
      })
      .subscribe({
        next: (contaAtualizada) => {
          this.state.conta.set(contaAtualizada);
          this.carregando = false;
          this.itemForm = { descricao: '', valor: 0, quantidade: 1 };
          this.selecaoItem.clear();
        },
        error: (err) => {
          this.carregando = false;
          this.erro = 'Não foi possível adicionar o item.';
        },
      });
  }

  calcularDivisao() {
    const conta = this.conta;
    if (!conta) {
      this.router.navigate(['/menu']);
      return;
    }
    if (conta.itens.length === 0) {
      this.erro = 'Adicione ao menos um item antes de calcular.';
      return;
    }
    this.router.navigate(['/divisao']);
  }

  formatarMoeda(valor: number): string {
    return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }
}