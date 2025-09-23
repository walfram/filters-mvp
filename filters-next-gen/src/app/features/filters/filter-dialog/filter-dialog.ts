import {Component, inject, ViewChild} from '@angular/core';
import {DialogRef} from '@angular/cdk/dialog';
import {Filter} from '../types/filter';
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from '@angular/material/dialog';
import {JsonPipe} from '@angular/common';
import {MatButton} from '@angular/material/button';
import {FilterForm} from '../filter-form/filter-form';

@Component({
  selector: 'app-filter-dialog',
  imports: [
    MatDialogContent,
    JsonPipe,
    MatDialogActions,
    MatButton,
    FilterForm,
    MatDialogTitle,
  ],
  templateUrl: './filter-dialog.html',
  styleUrl: './filter-dialog.css'
})
export class FilterDialog {
  @ViewChild(FilterForm, {static: true}) filterForm!: FilterForm;

  private dialogRef = inject<MatDialogRef<Filter>>(MatDialogRef<Filter>);
  private data: { filter: Filter } = inject<{filter: Filter}>(MAT_DIALOG_DATA);

  protected filter: Filter = this.data.filter;

  constructor() {
    console.log('FilterDialog.constructor, filter', this.filter);
  }

  onCancel() {
    this.dialogRef.close();
  }

  onSave() {
    console.log('triggering submit...');
    this.filterForm.triggerSubmit();
  }

  onSubmit(submittedFilter: Filter) {
    console.log('FormDialog.onSubmit', submittedFilter);
    this.dialogRef.close(submittedFilter);
  }
}
