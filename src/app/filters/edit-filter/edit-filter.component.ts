import {Component, Inject, Optional} from '@angular/core';
import {FilterFormComponent} from '../filter-form/filter-form.component';
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle} from '@angular/material/dialog';
import {MatButton} from '@angular/material/button';
import {NgIf} from '@angular/common';
import {Filter} from '../../shared/filter-schemas-and-types';
import {FILTER_SERVICE, FilterService} from '../services/filter-service';

@Component({
  selector: 'app-edit-filter',
  imports: [
    FilterFormComponent,
    MatButton,
    MatDialogActions,
    NgIf,
    MatDialogTitle,
    MatDialogContent,
    MatDialogClose
  ],
  templateUrl: './edit-filter.component.html',
  styleUrl: './edit-filter.component.css'
})
export class EditFilterComponent {
  constructor(
    @Optional() public dialogRef: MatDialogRef<EditFilterComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public filter: Filter,
    @Inject(FILTER_SERVICE) private filterService: FilterService
  ) {
    console.log(filter);
  }

  onFormSubmit(filter: Filter) {
    this.filterService.updateFilter(filter);
    if (this.dialogRef) {
      this.dialogRef.close(filter);
    } else {
      // handle regular mode submit
      console.log('Submitted in standalone mode', filter);
    }
  }

  cancel() {
    console.log('cancel edit filter');
    if (this.dialogRef) {
      this.dialogRef.close();
    }
  }
}
