import {FormArray, FormControl, FormGroup} from '@angular/forms';

export interface AmountCriterionForm {
  id: FormControl<string>;
  type: FormControl<'number'>;
  operator: FormControl<'eq' | 'gt' | 'gte' | 'lt' | 'lte'>;
  value: FormControl<number>;
}

export interface TitleCriterionForm {
  id: FormControl<string>;
  type: FormControl<'string'>;
  operator: FormControl<'eq' | 'contains' | 'startsWith' | 'endsWith'>;
  value: FormControl<string>;
  caseSensitive: FormControl<boolean>;
}

export interface DateCriterionForm {
  id: FormControl<string>;
  type: FormControl<'date'>;
  operator: FormControl<'before' | 'after' | 'equals'>;
  value: FormControl<Date>;
}

export type CriterionFormGroup =
  FormGroup<AmountCriterionForm> |
  FormGroup<TitleCriterionForm> |
  FormGroup<DateCriterionForm>;

export interface FilterFormGroup {
  id: FormControl<string>;
  name: FormControl<string>;
  criteria: FormArray<CriterionFormGroup>;
}
