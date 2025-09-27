import {Component, Input} from '@angular/core';
import {CriterionFormGroup} from '../../types/filter-form-group';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {JsonPipe} from '@angular/common';
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from '@angular/material/datepicker';
import {MatError, MatFormField, MatHint} from '@angular/material/form-field';
import {MatInput, MatLabel} from '@angular/material/input';
import {MatOption, MatSelect} from '@angular/material/select';

@Component({
  selector: 'app-date-controls',
  imports: [
    FormsModule,
    JsonPipe,
    MatDatepicker,
    MatDatepickerInput,
    MatDatepickerToggle,
    MatError,
    MatFormField,
    MatHint,
    MatInput,
    MatLabel,
    MatOption,
    MatSelect,
    ReactiveFormsModule
  ],
  templateUrl: './date-controls.html',
  styleUrl: './date-controls.css'
})
export class DateControls {
  @Input({required: true}) group!: CriterionFormGroup;

}
