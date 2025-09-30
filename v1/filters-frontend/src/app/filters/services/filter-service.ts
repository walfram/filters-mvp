import {Observable} from 'rxjs';
import {Filter, Filters} from '../../shared/filter-schemas-and-types';
import {InjectionToken} from '@angular/core';

export interface FilterService {
  filters(): Observable<Filters>;

  addFilter(filter: Filter): void;

  updateFilter(filter: Filter): void;

  deleteFilter(id: string): void;
}

export const FILTER_SERVICE = new InjectionToken<FilterService>('FilterService');
