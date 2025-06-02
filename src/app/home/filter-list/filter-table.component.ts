import { Component } from '@angular/core';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable
} from '@angular/material/table';
import {MatChip} from '@angular/material/chips';
import {MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {MatCard} from '@angular/material/card';
import {AddFilterComponent} from '../../filters/add-filter/add-filter.component';
import {MatDialog} from '@angular/material/dialog';
import {EditFilterComponent} from '../../filters/edit-filter/edit-filter.component';

@Component({
  selector: 'app-filter-table',
  imports: [
    MatTable,
    MatChip,
    MatHeaderCell,
    MatCell,
    MatHeaderCellDef,
    MatCellDef,
    MatColumnDef,
    MatIconButton,
    MatIcon,
    MatHeaderRow,
    MatRow,
    MatHeaderRowDef,
    MatRowDef,
    MatCard,
  ],
  templateUrl: './filter-table.component.html',
  styleUrl: './filter-table.component.css'
})
export class FilterTableComponent {
  displayedColumns: string[] = ['title', 'conditions', 'status', 'actions'];

  dataSource = [
    { title: 'Item A', conditions: 5, status: 'Active' },
    { title: 'Item B', conditions: 2, status: 'Not Active' },
    // Add more items as needed
  ];

  constructor(private dialog: MatDialog) {}

  openEditFilterDialog(item: any) {
    const dialogRef = this.dialog.open(EditFilterComponent, {
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

  toggleStatus(item: any) {
    item.status = item.status === 'Active' ? 'Not Active' : 'Active';
  }

  deleteItem(item: any) {
    this.dataSource = this.dataSource.filter(i => i !== item);
  }
}
