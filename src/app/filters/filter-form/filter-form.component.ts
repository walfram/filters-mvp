import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatFormField, MatHint, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {amountOperators, conditionTypes, dateOperators, Filter, FilterCondition, FilterSchema, titleOperators} from '../../shared/filter-schemas-and-types';
import {MatButton, MatIconButton} from '@angular/material/button';
import {MatSelect} from '@angular/material/select';
import {MatOption, provideNativeDateAdapter} from '@angular/material/core';
import {TitleCasePipe} from '@angular/common';
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from '@angular/material/datepicker';
import {MatIcon} from '@angular/material/icon';
import {ZodError} from 'zod';
import {MatCard, MatCardContent} from '@angular/material/card';

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
    MatHint,
    MatIconButton,
    MatIcon,
    MatCard,
    MatCardContent
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

  validationErrors: string[] = [];

  save() {
    try {
      const parsed = FilterSchema.parse(this.filterData);
      console.log('parsed', parsed);
      this.submitForm.emit(this.filterData);
    } catch (error) {
      if (error instanceof ZodError) {
        this.validationErrors = error.errors.map(e => e.message);
      }
    }
  }

  addCondition() {
    console.log('add condition');
    this.filterData.conditions.push({
      type: 'title',
      operator: 'contains',
      value: ''
    });
  }

  onConditionTypeChange(condition: FilterCondition, newType: string) {
    console.log('condition change', condition, 'new type', newType);
  }

  removeCondition(index: number) {
    this.filterData.conditions.splice(index, 1);
  }
}
