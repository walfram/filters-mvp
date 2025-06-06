import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {conditionTypeOptions, Filter, FilterCondition} from '../../shared/filter-schemas-and-types';
import {MatButton} from '@angular/material/button';
import {MatSelect} from '@angular/material/select';
import {MatOption} from '@angular/material/core';
import {TitleCasePipe} from '@angular/common';

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
  ],
  templateUrl: './filter-form.component.html',
  styleUrl: './filter-form.component.css'
})
export class FilterFormComponent {
  @Input() filterData!: Filter;
  @Output() submitForm = new EventEmitter<Filter>();

  protected readonly conditionTypeOptions = conditionTypeOptions;

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
