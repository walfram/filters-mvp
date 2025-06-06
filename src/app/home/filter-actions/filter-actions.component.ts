import {Component} from '@angular/core';
import {MatCard, MatCardActions} from '@angular/material/card';
import {MatButton} from '@angular/material/button';
import {MatDialog} from '@angular/material/dialog';
import {AddFilterComponent} from '../../filters/add-filter/add-filter.component';
import {Router} from '@angular/router';
import {Filter, newEmptyFilter} from '../../shared/filter-schemas-and-types';

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
  constructor(
    private dialog: MatDialog,
    private router: Router
  ) {
  }

  openAddFilterForm() {
    console.log('openAddFilterForm');
    void this.router.navigate(['/filter/add']);
  }

  openAddFilterDialog() {
    const dialogRef = this.dialog.open<AddFilterComponent, Filter, Filter>(AddFilterComponent, {
      width: '600px',
      data: {
        ...newEmptyFilter()
      },
      hasBackdrop: true,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog closed with result:', result);
      // Handle any result from the dialog if needed
    });
  }
}
