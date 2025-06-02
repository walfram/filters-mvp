import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';

@Component({
  selector: 'app-filter-form',
  imports: [
    FormsModule,
    MatFormField,
    MatInput,
    MatLabel
  ],
  templateUrl: './filter-form.component.html',
  styleUrl: './filter-form.component.css'
})
export class FilterFormComponent {
  @Input() filterData: any = {}; // for edit prefill
  @Output() submitForm = new EventEmitter<any>();

  save() {
    // validate and emit
    this.submitForm.emit(this.filterData);
  }
}
