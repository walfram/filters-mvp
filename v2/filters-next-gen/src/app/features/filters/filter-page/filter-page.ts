import {Component, inject} from '@angular/core';
import {RouterLink} from '@angular/router';
import {MatButton} from '@angular/material/button';
import {FilterList} from '../filter-list/filter-list';
import {Filter} from '../types/filter';
import {FilterService} from '../services/filter-service';
import {FilterDialog} from '../filter-dialog/filter-dialog';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';

@Component({
  selector: 'app-filter-page',
  imports: [
    RouterLink,
    MatButton,
    FilterList
  ],
  templateUrl: './filter-page.html',
  styleUrl: './filter-page.css'
})
export class FilterPage {

  private readonly filterService = inject(FilterService);
  private readonly dialog = inject(MatDialog);

  onShowFilterDialog(filter?: Filter) {
    console.log('showing filter add modal');

    const config = new MatDialogConfig();
    config.hasBackdrop = true;
    config.width = '80vw';
    config.maxWidth = '95vw';
    config.height = '80vh';
    config.maxHeight = '95vh';
    config.panelClass = 'resizable-dialog';
    config.disableClose = true;

    config.data = {
      filter: filter || {
        id: '',
        name: '',
        criteria: [],
        active: true
      } as Filter
    }

    const dialogRef = this.dialog.open<FilterDialog>(FilterDialog, config);

    dialogRef.afterClosed().subscribe(filter => {
      console.log('dialog closed', filter);
      if (!filter) {
        console.log('no filter on dialog close');
        return;
      }

      console.log('saving filter', filter);
      this.filterService.save(filter).subscribe(savedFilter => console.log('saved filter', savedFilter));
    });
  }
}
