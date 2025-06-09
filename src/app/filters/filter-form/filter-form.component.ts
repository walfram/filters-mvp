// filter-form.component.ts
import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatError, MatFormField, MatHint, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {
  amountOperators,
  conditionTypes,
  dateOperators,
  Filter,
  FilterCondition,
  FilterConditionType,
  FilterSchema,
  newEmptyCondition,
  titleOperators
} from '../../shared/filter-schemas-and-types';
import {MatButton, MatIconButton} from '@angular/material/button';
import {MatSelect} from '@angular/material/select';
import {MatOption, provideNativeDateAdapter} from '@angular/material/core';
import {TitleCasePipe} from '@angular/common';
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from '@angular/material/datepicker';
import {MatIcon} from '@angular/material/icon';
import {ZodError} from 'zod';

interface FieldError {
  field: string;
  message: string;
}

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
    MatError
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
  fieldErrors: FieldError[] = [];

  save() {
    try {
      const parsed = FilterSchema.parse(this.filterData);
      console.log('parsed', parsed);
      this.clearValidationErrors();
      this.submitForm.emit(this.filterData);
    } catch (error) {
      if (error instanceof ZodError) {
        console.log(error);
        this.processZodErrors(error);
      }
    }
  }

  private processZodErrors(error: ZodError) {
    this.validationErrors = [];
    this.fieldErrors = [];

    error.errors.forEach(err => {
      const path = err.path.slice(0, 2).join('.');

      // Add to general validation errors for display
      this.validationErrors.push(err.message);

      // Add to field-specific errors for highlighting
      this.fieldErrors.push({
        field: path,
        message: err.message
      });
    });
  }

  private clearValidationErrors() {
    this.validationErrors = [];
    this.fieldErrors = [];
  }

  // Helper method to check if a field has an error
  hasFieldError(fieldPath: string): boolean {
    return this.fieldErrors.some(error => error.field === fieldPath);
  }

  // Get error message for a specific field
  getFieldError(fieldPath: string): string | null {
    const error = this.fieldErrors.find(error => error.field === fieldPath);
    return error ? error.message : null;
  }

  // Helper methods for condition field errors
  hasConditionFieldError(conditionIndex: number): boolean {
    return this.hasFieldError(`conditions.${conditionIndex}`);
  }

  getConditionFieldError(conditionIndex: number): string | null {
    return this.getFieldError(`conditions.${conditionIndex}`);
  }

  addCondition() {
    console.log('add condition');
    if (!this.filterData.conditions) {
      this.filterData.conditions = [];
    }
    this.filterData.conditions.push({
      type: 'title',
      operator: 'contains',
      value: ''
    });
  }

  onConditionTypeChange(condition: FilterCondition, newType: FilterConditionType) {
    console.log('condition change', condition, 'new type', newType);

    const c = newEmptyCondition(newType);

    condition.type = newType;
    condition.operator = c.operator;
    condition.value = c.value;

    this.clearValidationErrors();
  }

  removeCondition(index: number) {
    this.filterData.conditions.splice(index, 1);
    // Clear validation errors when conditions change
    this.clearValidationErrors();
  }

  // Clear validation errors when user types (optional - for real-time clearing)
  onFieldChange() {
    if (this.fieldErrors.length > 0) {
      // Optionally clear errors on field change for better UX
      this.clearValidationErrors();
    }
  }
}
