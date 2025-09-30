import {Component, Inject} from '@angular/core';
import {Router} from '@angular/router';
import {Filter, newEmptyFilter} from '../../shared/filter-schemas-and-types';
import {FILTER_SERVICE, FilterService} from '../services/filter-service';
import {FilterFormComponent} from '../filter-form/filter-form.component';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-add-filter-page',
  templateUrl: './add-filter-page.component.html',
  styleUrl: './add-filter-page.component.css',
  standalone: true,
  imports: [
    FilterFormComponent,
    MatButton,
  ]
})
export class AddFilterPageComponent {
  filter: Filter = newEmptyFilter();

  constructor(
    @Inject(FILTER_SERVICE) private readonly filterService: FilterService,
    private readonly router: Router
  ) { }

  onFormSubmit(filter: Filter) {
    this.filterService.addFilter(filter);
    console.log('Filter added in page mode', filter);
    void this.router.navigate(['/']);
  }

  onCancel() {
    void this.router.navigate(['/']);
  }
}
