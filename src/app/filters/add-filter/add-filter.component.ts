import {Component, Inject, Optional} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from '@angular/material/dialog';
import {NgIf} from '@angular/common';
import {FilterFormComponent} from '../filter-form/filter-form.component';
import {MatButton} from '@angular/material/button';
import {Filter} from '../../shared/filter-schemas-and-types';

@Component({
  selector: 'app-add-filter',
  imports: [
    NgIf,
    FilterFormComponent,
    MatDialogActions,
    MatButton,
    MatDialogTitle,
    MatDialogContent
  ],
  templateUrl: './add-filter.component.html',
  styleUrl: './add-filter.component.css'
})
export class AddFilterComponent {
  constructor(
    @Optional() public dialogRef: MatDialogRef<AddFilterComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: Filter
  ) {}

  onFormSubmit(filter: Filter) {
    if (this.dialogRef) {
      this.dialogRef.close(filter);
    } else {
      // handle regular mode submit
      console.log('Submitted in standalone mode', filter);
    }
  }

  cancel() {
    console.log('cancel add filter');
    if (this.dialogRef) {
      this.dialogRef.close();
    }
  }
}
