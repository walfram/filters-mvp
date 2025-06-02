import {Component} from '@angular/core';
import {MatCard, MatCardActions} from '@angular/material/card';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-filter-actions',
  imports: [
    MatCard,
    MatCardActions,
    MatButton
  ],
  templateUrl: './filter-actions.component.html',
  styleUrl: './filter-actions.component.css'
})
export class FilterActionsComponent {

}
