import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatFormField, MatHint, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {amountOperators, conditionTypes, dateOperators, Filter, FilterCondition, titleOperators} from '../../shared/filter-schemas-and-types';
import {MatButton} from '@angular/material/button';
import {MatSelect} from '@angular/material/select';
import {MatOption} from '@angular/material/core';
import {TitleCasePipe} from '@angular/common';
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from '@angular/material/datepicker';
import {provideNativeDateAdapter} from '@angular/material/core';

@Component({
  selector: 'app-filter-form',
  imports: [
    FormsModule,
    MatFormField,
    MatInput,
    MatLabel,
    MatButton,
    MatSelect,
    MatOption,
    TitleCasePipe,
    MatDatepickerInput,
    MatDatepickerToggle,
    MatDatepicker,
    MatHint
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './filter-form.component.html',
  styleUrl: './filter-form.component.css'
})
export class FilterFormComponent {
  @Input() filterData!: Filter;
  @Output() submitForm = new EventEmitter<Filter>();

  protected readonly conditionTypes = conditionTypes;
  protected readonly titleOperators = titleOperators;
  protected readonly amountOperators = amountOperators;
  protected readonly dateOperators = dateOperators;

  save() {
    // validate and emit
    this.submitForm.emit(this.filterData);
  }

  addCondition() {
    console.log('add condition');
  }

  onConditionTypeChange(condition: FilterCondition, newType: string) {
    console.log('condition change', condition, 'new type', newType);
  }
}
