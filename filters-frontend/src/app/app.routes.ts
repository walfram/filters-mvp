import { Routes } from '@angular/router';
import {HomeComponent} from './home/home.component';
import { AddFilterPageComponent } from './filters/add-filter-page/add-filter-page.component';

export const routes: Routes = [
  {
    path: '', component: HomeComponent
  },
  { path: 'filter/add', component: AddFilterPageComponent },
];
