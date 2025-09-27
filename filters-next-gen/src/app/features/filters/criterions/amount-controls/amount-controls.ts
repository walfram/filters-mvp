import {Component, Input} from '@angular/core';
import {CriterionFormGroup} from '../../types/filter-form-group';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatError, MatFormField} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatOption, MatSelect} from '@angular/material/select';
import {JsonPipe} from '@angular/common';

@Component({
  selector: 'app-amount-controls',
  imports: [
    FormsModule,
    MatFormField,
    MatInput,
    MatOption,
    MatSelect,
    ReactiveFormsModule,
    JsonPipe,
    MatError
  ],
  templateUrl: './amount-controls.html',
  styleUrl: './amount-controls.css'
})
export class AmountControls {
  @Input({required: true}) group!: CriterionFormGroup;

}
