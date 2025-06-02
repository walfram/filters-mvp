import {Component, Inject, Optional} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogRef} from '@angular/material/dialog';
import {NgIf} from '@angular/common';
import {FilterFormComponent} from '../filter-form/filter-form.component';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-add-filter',
  imports: [
    NgIf,
    FilterFormComponent,
    MatDialogActions,
    MatButton
  ],
  templateUrl: './add-filter.component.html',
  styleUrl: './add-filter.component.css'
})
export class AddFilterComponent {
  constructor(
    @Optional() public dialogRef: MatDialogRef<AddFilterComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onFormSubmit(filter: any) {
    if (this.dialogRef) {
      this.dialogRef.close(filter);
    } else {
      // handle regular mode submit
      console.log('Submitted in standalone mode', filter);
    }
  }

  cancel() {
    if (this.dialogRef) {
      this.dialogRef.close();
    }
  }
}
