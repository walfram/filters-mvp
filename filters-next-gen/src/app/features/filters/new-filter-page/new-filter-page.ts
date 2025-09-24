import {Component, inject, ViewChild} from '@angular/core';
import {FilterForm} from '../filter-form/filter-form';
import {Filter} from '../types/filter';
import {Router} from '@angular/router';
import {MatButton} from '@angular/material/button';
import {FilterService} from '../services/filter-service';

@Component({
  selector: 'app-new-filter-page',
  imports: [
    FilterForm,
    MatButton
  ],
  templateUrl: './new-filter-page.html',
  styleUrl: './new-filter-page.css'
})
export class NewFilterPage {

  @ViewChild(FilterForm, {static: true}) filterForm!: FilterForm;

  private readonly router = inject(Router);
  private readonly filterService = inject(FilterService);

  protected readonly filter: Filter = {
    id: '',
    name: '',
    criteria: []
  };

  onSubmit(filter: Filter) {
    console.log('created filter', filter);
    this.filterService.save(filter).subscribe({
      next: () => {
        console.log('filter saved');
        this.router.navigate(['/filters']).then(() => console.log('cancel, returning to filters'));
      },
      error: (err) => {
        console.error('error saving filter', err);
      }
    });
  }

  save() {
    this.filterForm.triggerSubmit();
  }

  reset() {
    this.router.navigate(['/filters']).then(() => console.log('cancel, returning to filters'));
  }
}
