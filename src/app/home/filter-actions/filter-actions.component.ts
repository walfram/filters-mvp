import {Component} from '@angular/core';
import {MatCard, MatCardActions} from '@angular/material/card';
import {MatButton} from '@angular/material/button';
import {MatDialog} from '@angular/material/dialog';
import {AddFilterComponent} from '../../filters/add-filter/add-filter.component';

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
  constructor(private dialog: MatDialog) {}

  openAddFilterForm() {
    console.log('openAddFilterForm');
  }

  openAddFilterDialog() {
    const dialogRef = this.dialog.open(AddFilterComponent, {
      width: '400px', // optional
      data: {
        // pass any data to AddFilterComponent here
      },
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog closed with result:', result);
      // Handle any result from the dialog if needed
    });
  }
}
