import {Component, Input} from '@angular/core';
import {CriterionFormGroup} from '../../types/filter-form-group';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {JsonPipe} from '@angular/common';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatError, MatFormField, MatHint, MatSuffix} from '@angular/material/form-field';
import {MatInput, MatLabel} from '@angular/material/input';
import {MatOption, MatSelect} from '@angular/material/select';

@Component({
  selector: 'app-date-controls',
  imports: [
    FormsModule,
    JsonPipe,
    MatDatepickerModule,
    MatError,
    MatFormField,
    MatHint,
    MatInput,
    MatLabel,
    MatOption,
    MatSelect,
    ReactiveFormsModule,
    MatSuffix
  ],
  templateUrl: './date-controls.html',
  styleUrl: './date-controls.css'
})
export class DateControls {
  @Input({required: true}) group!: CriterionFormGroup;

}
