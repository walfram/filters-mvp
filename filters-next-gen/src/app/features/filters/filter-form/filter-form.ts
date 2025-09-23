import {Component, EventEmitter, inject, Input, OnInit, Output} from '@angular/core';
import {Filter} from '../types/filter';
import {FormArray, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatButton, MatMiniFabButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {AmountCriterion, Criterion, DateCriterion, TitleCriterion} from '../types/criterion';
import {v4} from 'uuid';
import {MatOption, MatSelect} from '@angular/material/select';
import {MatCheckbox} from '@angular/material/checkbox';

// interface AmountCriterionControls {}
// interface TitleCriterionControls {}
// interface DateCriterionControl {}

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
    MatCheckbox
  ],
  templateUrl: './filter-form.html',
  styleUrl: './filter-form.css'
})
export class FilterForm implements OnInit {

  @Input({required: true}) filter!: Filter;
  @Output() submit = new EventEmitter<Filter>();
  // @Output() cancel = new EventEmitter<void>();

  private readonly formBuilder = inject(NonNullableFormBuilder);

  protected form!: FormGroup;

  constructor() {
    console.log('FilterForm.constructor, filter', this.filter);
  }

  get criteria() {
    return this.form.get('criteria') as FormArray;
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      name: this.formBuilder.control(this.filter.name, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      criteria: this.formBuilder.array(
        this.filter.criteria.map(c => this.createCriterionGroup(c)
        ), [Validators.required])
    });
  }

  private createCriterionGroup(criterion: Criterion): FormGroup {
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

  private createAmountCriterionGroup(criterion: AmountCriterion): FormGroup {
    return this.formBuilder.group({
      id: [criterion.id ?? ''],
      type: ['number', Validators.required],
      operator: [criterion?.operator ?? 'eq', Validators.required],
      value: [criterion?.value ?? 0, Validators.required],
    });
  }

  private createTitleCriterionGroup(criterion: TitleCriterion): FormGroup {
    return this.formBuilder.group({
      id: [criterion?.id ?? ''],
      type: ['string', Validators.required],
      operator: [criterion?.operator ?? 'eq', Validators.required],
      value: [criterion?.value ?? '', Validators.required],
      caseSensitive: [criterion?.caseSensitive ?? false],
    });
  }

  private createDateCriterionGroup(criterion?: DateCriterion): FormGroup {
    return this.formBuilder.group({
      id: [criterion?.id ?? ''],
      type: ['date', Validators.required],
      operator: [criterion?.operator ?? 'equals', Validators.required],
      value: [criterion?.value ?? null, Validators.required],
    });
  }

  // private addCriterion(type: 'number' | 'string' | 'date') {
  //   const criteriaArray = this.filterForm.get('criteria') as FormArray;
  //   switch (type) {
  //     case 'number':
  //       criteriaArray.push(this.createAmountCriterionGroup());
  //       break;
  //     case 'string':
  //       criteriaArray.push(this.createTitleCriterionGroup());
  //       break;
  //     case 'date':
  //       criteriaArray.push(this.createDateCriterionGroup());
  //       break;
  //   }
  // }

  onSubmit() {
    if (this.form.valid) {
      this.submit.emit({
        ...this.form.getRawValue(),
        id: this.filter.id,
        criteria: [] // TODO fixme :)
      });
    } else {
      console.log('invalid form');
    }
  }

  triggerSubmit() {
    this.onSubmit();
  }

  onTypeChange(index: number) {
    const control = this.criteria.at(index);
    const type = control.get('type')?.value;

    const id = control.get('id')?.value as string;

    // Replace this FormGroup with a new one for the chosen type
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
    // TODO AmountCriterion is default
    const criterion = this.formBuilder.group({
      id: v4(),
      field: '',
      operator: '',
      value: ''
    });
    this.criteria.push(criterion);

    console.log('criteria', this.criteria.length);
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
}
