import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {Filter} from '../../shared/filter-schemas-and-types';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-filter-form',
  imports: [
    FormsModule,
    MatFormField,
    MatInput,
    MatLabel,
    NgIf
  ],
  templateUrl: './filter-form.component.html',
  styleUrl: './filter-form.component.css'
})
export class FilterFormComponent {
  @Input() filterData!: Filter;
  @Output() submitForm = new EventEmitter<Filter>();

  save() {
    // validate and emit
    this.submitForm.emit(this.filterData);
  }
}
