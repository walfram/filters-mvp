import { Injectable } from '@angular/core';
import { FilterService } from './filter-service';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Filter } from '../../shared/filter-schemas-and-types';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class RestFilterService implements FilterService {

  private readonly apiUrl = 'http://localhost:8080/api/filters';
  private readonly refreshFilters$ = new Subject<void>();

  constructor(private readonly http: HttpClient) {}

  filters(): Observable<Filter[]> {
    return this.refreshFilters$.pipe(
      startWith(null),
      switchMap(() => this.http.get<Filter[]>(this.apiUrl))
    );
  }

  addFilter(filter: Filter): void {
    this.http.post(this.apiUrl, filter).subscribe(() => {
      this.refreshFilters$.next();
    });
  }

  updateFilter(filter: Filter): void {
    this.http.put(`${this.apiUrl}/${filter.id}`, filter).subscribe(() => {
      this.refreshFilters$.next();
    });
  }

  deleteFilter(id: string): void {
    this.http.delete(`${this.apiUrl}/${id}`).subscribe(() => {
      this.refreshFilters$.next();
    });
  }

}
