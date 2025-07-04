import {Component, Inject, OnInit} from '@angular/core';
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
import {MatDialog} from '@angular/material/dialog';
import {EditFilterComponent} from '../../filters/edit-filter/edit-filter.component';
import {Filter} from '../../shared/filter-schemas-and-types';
import {FILTER_SERVICE, FilterService} from '../../filters/services/filter-service';

@Component({
  selector: 'app-filter-list',
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
  templateUrl: './filter-list.component.html',
  styleUrl: './filter-list.component.css'
})
export class FilterListComponent implements OnInit {
  displayedColumns: string[] = ['title', 'conditions', 'status', 'actions'];

  dataSource: Filter[] = [];

  constructor(
    private readonly dialog: MatDialog,
    @Inject(FILTER_SERVICE) private readonly filterService: FilterService
  ) {
  }

  ngOnInit(): void {
    this.filterService.filters()
      .subscribe((filters: Filter[]) => {
        this.dataSource = filters;
      });
  }

  openEditFilterDialog(filter: Filter) {
    const dialogRef = this.dialog.open<EditFilterComponent, Filter, Filter>(EditFilterComponent, {
      maxWidth: '90vw',
      maxHeight: '90vh',
      data: {
        ...filter
      },
      hasBackdrop: true,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog closed with result:', result);
    });
  }

  toggleStatus(filter: Filter) {
    console.log(filter);
    filter.active = !filter.active;
    this.filterService.updateFilter(filter);
  }

  deleteFilter(filter: Filter) {
    // TODO confirmation
    this.filterService.deleteFilter(filter.id);
  }
}
