import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home.component';
import { GroupListComponent } from './pages/group-list/group-list.component';
import {GroupEditComponent} from './pages/group-edit/group-edit.component';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'groups',
    component: GroupListComponent
  },
  {
    path: 'group/:id',
    component: GroupEditComponent
  }
];
