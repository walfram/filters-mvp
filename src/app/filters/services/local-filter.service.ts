import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {Filter, Filters} from '../../shared/filter-schemas-and-types';
import {MOCKED_FILTERS} from '../../mocks/mocked-filters';
import {FilterService} from './filter-service';

@Injectable({
  providedIn: 'root'
})
export class LocalFilterService implements FilterService {

  private filters$: BehaviorSubject<Filters>;

  constructor() {
    this.filters$ = new BehaviorSubject<Filters>(MOCKED_FILTERS);
  }

  filters(): Observable<Filters> {
    return this.filters$.asObservable();
  }

  addFilter(filter: Filter): void {
    const current = this.filters$.getValue();
    this.filters$.next([...current, filter]);
  }

  updateFilter(filter: Filter): void {
    const current = this.filters$.getValue();
    const index = current.findIndex(f => f.id === filter.id);

    if (index === -1) {
      throw new Error(`Filter with id ${filter.id} not found`);
    }

    const updated = [...current];
    updated[index] = filter;
    this.filters$.next(updated);
  }

  deleteFilter(id: string): void {
    const current = this.filters$.getValue();
    const updated = current.filter(f => f.id !== id);

    if (updated.length === current.length) {
      throw new Error(`Filter with id ${id} not found`);
    }

    this.filters$.next(updated);
  }
}
