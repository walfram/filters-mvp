import {Component, EventEmitter, inject, Input, OnInit, Output} from '@angular/core';
import {Filter} from '../types/filter';
import {FormArray, FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormField, MatHint, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatButton, MatMiniFabButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {AmountCriterion, Criterion, DateCriterion, TitleCriterion} from '../types/criterion';
import {v4} from 'uuid';
import {MatOption, MatSelect} from '@angular/material/select';
import {MatCheckbox} from '@angular/material/checkbox';
import {NgClass} from '@angular/common';
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from '@angular/material/datepicker';
import {provideNativeDateAdapter} from '@angular/material/core';

interface AmountCriterionForm {
  id: FormControl<string>;
  type: FormControl<'number'>;
  operator: FormControl<'eq' | 'gt' | 'gte' | 'lt' | 'lte'>;
  value: FormControl<number>;
}

interface TitleCriterionForm {
  id: FormControl<string>;
  type: FormControl<'string'>;
  operator: FormControl<'eq' | 'contains' | 'startsWith' | 'endsWith'>;
  value: FormControl<string>;
  caseSensitive: FormControl<boolean>;
}

interface DateCriterionForm {
  id: FormControl<string>;
  type: FormControl<'date'>;
  operator: FormControl<'before' | 'after' | 'equals'>;
  value: FormControl<Date>;
}

type CriterionFormGroup =
  FormGroup<AmountCriterionForm> |
  FormGroup<TitleCriterionForm> |
  FormGroup<DateCriterionForm>;

interface FilterFormGroup {
  id: FormControl<string>;
  name: FormControl<string>;
  criteria: FormArray<CriterionFormGroup>;
}

@Component({
  selector: 'app-filter-form',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatInput,
    MatButton,
    MatMiniFabButton,
    MatIcon,
    MatSelect,
    MatOption,
    MatCheckbox,
    NgClass,
    MatHint,
    MatDatepickerToggle,
    MatDatepicker,
    MatDatepickerInput
  ],
  templateUrl: './filter-form.html',
  styleUrl: './filter-form.css',
  providers: [provideNativeDateAdapter()]
})
export class FilterForm implements OnInit {

  @Input({required: true}) filter!: Filter;
  @Output() submit = new EventEmitter<Filter>();

  private readonly formBuilder = inject(NonNullableFormBuilder);

  protected form!: FormGroup<FilterFormGroup>;

  get criteria() {
    return this.form.controls.criteria;
  }

  ngOnInit(): void {
    const groups: CriterionFormGroup[]
      = this.filter.criteria.map(c => this.createCriterionGroup(c));

    this.form = this.formBuilder.group<FilterFormGroup>({
      id: this.formBuilder.control(this.filter.id, [Validators.required]),
      name: this.formBuilder.control(this.filter.name, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      // new FormArray and not this.formBuilder.array 'cause TypeScript was going nuts
      criteria: new FormArray<CriterionFormGroup>(groups, [Validators.required])
    });
  }

  private createCriterionGroup(criterion: Criterion): CriterionFormGroup {
    switch (criterion.type) {
      case 'number':
        return this.createAmountCriterionGroup(criterion);
      case 'string':
        return this.createTitleCriterionGroup(criterion);
      case 'date':
        return this.createDateCriterionGroup(criterion);
      default:
        throw new Error('Unknown criterion type');
    }
  }

  private createAmountCriterionGroup(criterion: AmountCriterion): FormGroup<AmountCriterionForm> {
    return this.formBuilder.group<AmountCriterionForm>({
      id: this.formBuilder.control(criterion.id),
      type: this.formBuilder.control('number', [Validators.required]),
      operator: this.formBuilder.control(criterion.operator, [Validators.required]),
      value: this.formBuilder.control(criterion.value, [Validators.required])
    });
  }

  private createTitleCriterionGroup(criterion: TitleCriterion): FormGroup<TitleCriterionForm> {
    return this.formBuilder.group<TitleCriterionForm>({
      id: this.formBuilder.control(criterion.id),
      type: this.formBuilder.control('string', [Validators.required]),
      operator: this.formBuilder.control(criterion.operator, [Validators.required]),
      value: this.formBuilder.control(criterion.value, [Validators.required]),
      caseSensitive: this.formBuilder.control(criterion.caseSensitive)
    });
  }

  private createDateCriterionGroup(criterion: DateCriterion): FormGroup<DateCriterionForm> {
    return this.formBuilder.group<DateCriterionForm>({
      id: this.formBuilder.control(criterion.id),
      type: this.formBuilder.control('date', [Validators.required]),
      operator: this.formBuilder.control(criterion.operator, [Validators.required]),
      value: this.formBuilder.control(criterion.value, [Validators.required])
    });
  }

  onSubmit() {
    console.log('raw value', this.form.getRawValue());
    console.log('value', this.form.value);

    if (this.form.valid) {
      this.submit.emit({
        ...this.form.getRawValue(),
        id: this.filter.id
      });
    } else {
      console.log('invalid form');
    }
  }

  triggerSubmit() {
    this.onSubmit();
  }

  onTypeChange(type: string, index: number) {
    console.log('onTypeChange @', index);
    const control = this.criteria.at(index);
    console.log('control', control);
    // const type = control.get('type')?.value;
    console.log('type', type);
    const id = control.controls.id.value;
    console.log('id', id);

    switch (type) {
      case 'number':
        this.criteria.setControl(index, this.createAmountCriterionGroup(this.emptyAmountCriterion(id)));
        break;
      case 'string':
        this.criteria.setControl(index, this.createTitleCriterionGroup(this.emptyTitleCriterion(id)));
        break;
      case 'date':
        this.criteria.setControl(index, this.createDateCriterionGroup(this.emptyDateCriterion(id)));
        break;
    }
  }

  addCriterion() {
    this.criteria.push(this.createAmountCriterionGroup(this.emptyAmountCriterion(v4())));
    console.log('added criterion, criteria.length', this.criteria.length);
  }

  removeCriterion(idx: number) {
    console.log('removing criterion @', idx);
    this.criteria.removeAt(idx);
  }

  private emptyAmountCriterion(id: string): AmountCriterion {
    return {
      id,
      operator: 'eq',
      type: 'number',
      value: 0
    };
  }

  private emptyTitleCriterion(id: string): TitleCriterion {
    return {
      id,
      operator: 'eq',
      type: 'string',
      value: '',
      caseSensitive: false
    };
  }

  private emptyDateCriterion(id: string): DateCriterion {
    return {
      id,
      operator: 'equals',
      type: 'date',
      value: new Date()
    }
  }

  protected readonly String = String;
}
