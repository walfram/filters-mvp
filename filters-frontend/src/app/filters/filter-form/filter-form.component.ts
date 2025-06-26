import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {
  AmountOperator,
  amountOperators,
  conditionTypes,
  DateOperator, dateOperators,
  Filter,
  FilterCondition,
  FilterConditionType,
  FilterSchema, newEmptyCondition,
  TitleOperator,
  titleOperators
} from '../../shared/filter-schemas-and-types';
import {zodValidator} from '../../shared/zod-validator';
import {v4} from 'uuid';
import {MatError, MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatOption, MatSelect} from '@angular/material/select';
import {MatCheckbox} from '@angular/material/checkbox';
import {MatButton, MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {TitleCasePipe} from '@angular/common';

@Component({
  selector: 'app-filter-form',
  styleUrls: ['./filter-form.component.css'],
  templateUrl: './filter-form.component.html',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatCheckbox,
    MatInput,
    MatSelect,
    MatOption,
    MatIcon,
    MatError,
    MatButton,
    MatIconButton,
    MatLabel,
    TitleCasePipe
  ]
})
export class FilterFormComponent implements OnInit {
  @Output() submitForm = new EventEmitter<Filter>();
  @Input() filterData!: Filter;

  form!: FormGroup;
  submitted: boolean = false;

  protected readonly conditionTypes = conditionTypes;

  constructor(private fb: FormBuilder) {
  }

  ngOnInit(): void {
    console.log('this.filterData', this.filterData);
    this.form = this.createFilterForm(this.filterData);
  }

  createFilterForm(filter?: Filter): FormGroup {
    return this.fb.nonNullable.group(
      {
        id: this.fb.control(filter?.id ?? v4()),
        name: this.fb.nonNullable.control(filter?.name ?? '', {
          validators: [Validators.required],
        }),
        active: this.fb.nonNullable.control(filter?.active ?? true),
        conditions: this.fb.nonNullable.array(filter?.conditions.map(c => this.createConditionForm(c)) ?? []),
      },
      {
        validators: [zodValidator(FilterSchema)],
      }
    );
  }

  createConditionForm(condition?: FilterCondition) {
    console.log('createConditionForm', condition);
    return this.fb.nonNullable.group({
      type: this.fb.nonNullable.control(condition?.type ?? '', [Validators.required]),
      operator: this.fb.nonNullable.control(condition?.operator ?? '', [Validators.required]),
      value: this.fb.nonNullable.control(condition?.value ?? '', [Validators.required]),
    });
  }

  get conditions() {
    return this.form.get('conditions') as FormArray;
  }

  addCondition() {
    this.conditions.push(this.createConditionForm( newEmptyCondition("title") ));

    this.form.markAsUntouched();
    this.submitted = false;
  }

  removeCondition(index: number) {
    this.conditions.removeAt(index);

    this.form.markAsUntouched();
    this.submitted = false;
  }

  onSubmit() {
    this.submitted = true;

    if (this.form.invalid) {
      this.form.markAllAsTouched(); 
      console.warn('Form invalid:', this.form.errors);
      return;
    }

    const filter: Filter = this.form.value;
    console.log('Submitting filter:', filter);
    this.submitForm.emit(filter);
  }

  operators(type: FilterConditionType): readonly TitleOperator[] | readonly AmountOperator[] | readonly DateOperator[] {
    switch (type) {
      case 'amount':
        return amountOperators;
      case 'title':
        return titleOperators;
      case 'date':
        return dateOperators;
      default:
        throw new Error(`Unsupported condition type: ${type}`);
    }
  }

  onFieldChange(index: number, value: string) {
    const conditionGroup = this.conditions.at(index);
    if (conditionGroup) {
      conditionGroup.get('type')?.setValue(value);
      conditionGroup.get('operator')?.reset('');
      conditionGroup.get('value')?.reset('');
    }
  }

  onOperatorChange() {
    console.log('onOperatorChange called');
  }
}
