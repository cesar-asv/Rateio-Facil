import { Routes } from '@angular/router';
import { LoginComponent } from './login/login';
import { MenuComponent } from './menu/menu';
import { ParticipantesComponent } from './participantes/participantes';
import { ItensComponent } from './itens/itens';
import { DivisaoComponent } from './divisao/divisao';
import { HistoricoComponent } from './historico/historico';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'menu', component: MenuComponent },
  { path: 'participantes', component: ParticipantesComponent },
  { path: 'itens', component: ItensComponent },
  { path: 'divisao', component: DivisaoComponent },
  { path: 'historico', component: HistoricoComponent },
];