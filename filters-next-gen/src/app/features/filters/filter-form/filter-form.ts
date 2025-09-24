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

  private readonly formBuilder = inject(NonNullableFormBuilder);

  protected form!: FormGroup;

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
      id: [criterion.id],
      type: ['number', Validators.required],
      operator: [criterion.operator, Validators.required],
      value: [criterion.value, Validators.required],
    });
  }

  private createTitleCriterionGroup(criterion: TitleCriterion): FormGroup {
    return this.formBuilder.group({
      id: [criterion.id],
      type: ['string', Validators.required],
      operator: [criterion.operator, Validators.required],
      value: [criterion.value, Validators.required],
      caseSensitive: [criterion.caseSensitive],
    });
  }

  private createDateCriterionGroup(criterion: DateCriterion): FormGroup {
    return this.formBuilder.group({
      id: [criterion.id],
      type: ['date', Validators.required],
      operator: [criterion.operator, Validators.required],
      value: [criterion.value, Validators.required],
    });
  }

  onSubmit() {
    if (this.form.valid) {
      this.submit.emit({
        ...this.form.getRawValue(),
        id: this.filter.id
        // criteria: [] // TODO fixme :)
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
    const id = control.get('id')?.value as string;
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
}
