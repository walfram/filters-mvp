import {Component} from '@angular/core';
import {FilterActionsComponent} from './filter-actions/filter-actions.component';
import {FilterListComponent} from './filter-list/filter-list.component';

@Component({
  selector: 'app-home',
  imports: [
    FilterActionsComponent,
    FilterListComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
