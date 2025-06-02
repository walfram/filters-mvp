import {Component} from '@angular/core';
import {FilterActionsComponent} from './filter-actions/filter-actions.component';
import {FilterTableComponent} from './filter-list/filter-table.component';

@Component({
  selector: 'app-home',
  imports: [
    FilterActionsComponent,
    FilterTableComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
