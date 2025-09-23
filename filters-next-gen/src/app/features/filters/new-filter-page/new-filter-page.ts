import {Component, inject} from '@angular/core';
import {FilterForm} from '../filter-form/filter-form';
import {Filter} from '../types/filter';
import {Router} from '@angular/router';
import {MatButton} from '@angular/material/button';

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

  private readonly router = inject(Router);

  protected readonly filter: Filter = {
    id: '',
    name: '',
    criteria: []
  };

  onSubmit(filter: Filter) {
    console.log('created filter', filter);
  }

  save() {
    console.log('new-filter-page.save, saving filter', this.filter);
  }

  reset() {
    this.router.navigate(['/filters']).then(() => console.log('cancel, returning to filters'));
  }
}
