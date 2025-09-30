import {Component, Input} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCheckbox} from "@angular/material/checkbox";
import {MatError, MatFormField} from "@angular/material/form-field";
import {MatInput, MatLabel} from "@angular/material/input";
import {MatOption, MatSelect} from "@angular/material/select";
import {CriterionFormGroup} from '../../types/filter-form-group';
import {JsonPipe} from '@angular/common';

@Component({
  selector: 'app-title-controls',
  imports: [
    FormsModule,
    MatCheckbox,
    MatFormField,
    MatInput,
    MatLabel,
    MatOption,
    MatSelect,
    ReactiveFormsModule,
    JsonPipe,
    MatError
  ],
  templateUrl: './title-controls.html',
  styleUrl: './title-controls.css'
})
export class TitleControls {
  @Input({required: true}) group!: CriterionFormGroup;
}
