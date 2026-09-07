import { Routes } from '@angular/router';
import { LoginComponent } from './login/login';
import { ParticipantesComponent } from './participantes/participantes';
import { ItensComponent } from './itens/itens';
import { DivisaoComponent } from './divisao/divisao';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'participantes', component: ParticipantesComponent },
  { path: 'itens', component: ItensComponent },
  { path: 'divisao', component: DivisaoComponent },
];