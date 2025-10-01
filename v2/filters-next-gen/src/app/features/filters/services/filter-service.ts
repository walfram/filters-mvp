import {inject, Injectable} from '@angular/core';
import {BehaviorSubject, catchError, finalize, Observable, tap} from 'rxjs';
import {Filter} from '../types/filter';
import {LoaderService} from '../../../shared/services/loader-service';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FilterService {

  private readonly filtersSubject = new BehaviorSubject<Filter[]>([]);
  public readonly filters$ = this.filtersSubject.asObservable();

  private readonly loaderService = inject(LoaderService);
  private readonly httpClient = inject(HttpClient);

  constructor() {
    this.loadFilters();
  }

  private loadFilters() {
    console.log('loading filters');
    this.loaderService.show();

    this.httpClient.get<Filter[]>('/api/filter').subscribe({
      next: filters => {
        console.log('loaded filters', filters);
        const revived = this.reviveDates(filters)
        this.filtersSubject.next(revived);
      },
      error: error => {
        console.error('error loading filters', error);
        this.filtersSubject.next([]);
      },
      complete: () => this.loaderService.hide()
    });
  }

  save(filter: Filter) {
    this.loaderService.show();

    const currentFilters = this.filtersSubject.getValue();
    const tempFilterId = this.tempFilterId();

    const optimistic = {
      ...filter,
      id: filter.id || tempFilterId
    }

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
      }),
      finalize(() => this.loaderService.hide())
    );
  }

  // tslint:disable-next-line:unused-variable
  private debugRandomDelay() {
    const minDelay = 500;
    const maxDelay = 5000;
    return Math.floor(Math.random() * (maxDelay - minDelay + 1)) + minDelay;
  }

  private put(filter: Filter): Observable<Filter> {
    return this.httpClient.put<Filter>(`/api/filter/${filter.id}`, filter);
  }

  private post(filter: Filter): Observable<Filter> {
    console.log('saving to backend', filter);
    return this.httpClient.post<Filter>('/api/filter', filter);
  }

  private tempFilterId(): string {
    return `temp-filter-${Date.now()}`;
  }

  private del(filter: Filter): Observable<Filter> {
    return this.httpClient.delete<Filter>(`/api/filter/${filter.id}`);
  }

  delete(filter: Filter): Observable<Filter> {
    this.loaderService.show();

    const currentFilters = this.filtersSubject.getValue();
    const updatedFilters = currentFilters.filter(f => f.id !== filter.id);

    return this.del(filter).pipe(
      tap(() => this.filtersSubject.next(updatedFilters)),
      catchError(error => {
        console.log('could not delete filter', error);
        throw error;
      }),
      finalize(() => this.loaderService.hide())
    )
  }

  private reviveDates(filters: Filter[]) {
    return filters.map(filter => {
      if (filter.criteria && Array.isArray(filter.criteria)) {
        filter.criteria = filter.criteria.map((criterion: any) => {
          if (criterion.type === 'date' && typeof criterion.value === 'string') {
            criterion.value = new Date(criterion.value);
          }
          return criterion;
        });
      }
      return filter;
    });
  }
}
