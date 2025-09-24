import { Injectable } from '@angular/core';
import {BehaviorSubject, catchError, delay, map, Observable, of, tap} from 'rxjs';
import {v4} from 'uuid';
import {Filter} from '../types/filter';
import {SAMPLE_FILTERS} from '../mocks/sample-filters';

@Injectable({
  providedIn: 'root'
})
export class FilterService {

  private readonly filtersSubject = new BehaviorSubject<Filter[]>([]);

  constructor() {
    this.loadFilters();
  }

  private loadFilters() {
    console.log('loading filters');
    this.filtersSubject.next(SAMPLE_FILTERS);
  }

  filters(): Observable<Filter[]> {
    return this.filtersSubject.asObservable();
  }

  save(filter: Filter) {
    const currentFilters = this.filtersSubject.getValue();
    const tempFilterId = this.tempFilterId();

    const optimistic = {
      ...filter,
      id: filter.id || tempFilterId
    }

    // TODO not sure if this will preserve optimistic filter in state - check

    if (filter.id) {
      // update
      this.filtersSubject.next(currentFilters.map(f => f.id === filter.id ? optimistic : f));
    } else {
      // save
      this.filtersSubject.next([optimistic, ...currentFilters]);
    }

    const request$ = filter.id ?
      this.put(filter) : this.post(filter);

    return request$.pipe(
      tap(savedFilter => {
        const filters = this.filtersSubject.getValue();
        const updated = filters.map(f => (f.id === savedFilter.id || f.id === tempFilterId) ? savedFilter : f);
        this.filtersSubject.next(updated);
      }),
      catchError(error => {
        console.error('error saving filter', error);
        this.filtersSubject.next(currentFilters);
        throw error;
      })
    );
  }

  private debugRandomDelay() {
    const minDelay = 500;
    const maxDelay = 5000;
    return Math.floor(Math.random() * (maxDelay - minDelay + 1)) + minDelay;
  }

  private put(filter: Filter): Observable<Filter> {
    return of(filter).pipe(
      delay(this.debugRandomDelay())
    );
  }

  private post(filter: Filter): Observable<Filter> {
    console.log('saving to backend', filter);
    const d = this.debugRandomDelay();
    return of(filter).pipe(
      delay(d),
      map(f => {
        f.id = v4();
        console.log('new filter id', f.id);
        return f;
      })
    );
  }

  private tempFilterId(): string {
    return `temp-filter-${Date.now()}`;
  }
}
