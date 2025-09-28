import {Component, inject, Input, OnInit} from '@angular/core';
import {FilterService} from '../services/filter-service';
import {AsyncPipe} from '@angular/common';
import {Filter} from '../types/filter';
import {Observable} from 'rxjs';
import {MatMiniFabButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {CriterionToStringPipe} from '../../../shared/pipes/criterion-to-string-pipe';
import {YesNoDialog, YesNoDialogOptions} from '../../../shared/components/yes-no-dialog/yes-no-dialog';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-filter-list',
  imports: [
    AsyncPipe,
    MatIcon,
    MatMiniFabButton,
    CriterionToStringPipe
  ],
  templateUrl: './filter-list.html',
  styleUrl: './filter-list.css'
})
export class FilterList implements OnInit {
  @Input({required: true}) showFilterDialog!: (filter?: Filter) => void;

  private readonly snackbar = inject(MatSnackBar);

  protected readonly filterService = inject(FilterService);
  protected filters$: Observable<Filter[]> | undefined;

  private readonly dialog = inject(MatDialog);

  ngOnInit(): void {
    this.filters$ = this.filterService.filters$;
  }

  showDeleteConfirmDialog(filter: Filter) {
    const dialogData: YesNoDialogOptions = {
      title: 'Confirm Action',
      message: 'Are you sure you want to proceed with this action?'
    };

    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '400px';
    dialogConfig.data = dialogData;

    const dialogRef = this.dialog.open<YesNoDialog>(YesNoDialog, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.filterService.delete(filter).subscribe({
          next: filter => this.snackbar.open(`removed filter ${filter.id}`, 'Ok', {duration: 5000}),
          error: error => this.snackbar.open(`error removing filter ${filter.id}, ${error.message || error}`, 'Ok', {duration: 5000}),
        });
      } else {
        console.log('User clicked No');
        // Handle "No" action
      }
    });
  }
}
