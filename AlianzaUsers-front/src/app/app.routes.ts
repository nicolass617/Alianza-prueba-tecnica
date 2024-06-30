import { Routes } from '@angular/router';
import { ClientsListComponent } from './components/clients-list/clients-list.component';

export const routes: Routes = [
    { path: '', component: ClientsListComponent },
    { path: 'clients', component: ClientsListComponent }
];
