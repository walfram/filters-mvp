import {Component, EventEmitter, inject, Input, OnInit, Output} from '@angular/core';
import {Filter} from '../types/filter';
import {JsonPipe} from '@angular/common';
import {FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule} from '@angular/forms';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';

@Component({
  selector: 'app-filter-form',
  imports: [
    JsonPipe,
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatInput
  ],
  templateUrl: './filter-form.html',
  styleUrl: './filter-form.css'
})
export class FilterForm implements OnInit {

  @Input({required: true}) filter!: Filter;
  @Output() submit = new EventEmitter<Filter>();
  // @Output() cancel = new EventEmitter<void>();

  private readonly formBuilder = inject(NonNullableFormBuilder);

  protected form!: FormGroup<{
    name: FormControl<string>;
  }>;

  constructor() {
    console.log('FilterForm.constructor, filter', this.filter);
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      name: this.formBuilder.control(this.filter.name),
    });
  }

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
}
