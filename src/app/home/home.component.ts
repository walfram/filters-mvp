import { Component } from '@angular/core';
import {FilterActionsComponent} from './filter-actions/filter-actions.component';

@Component({
  selector: 'app-home',
  imports: [
    FilterActionsComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
