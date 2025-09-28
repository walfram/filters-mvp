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
        this.filterService.delete(filter);
      } else {
        console.log('User clicked No');
        // Handle "No" action
      }
    });
  }
}
