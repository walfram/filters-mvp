import {Injectable} from '@angular/core';
import {FilterService} from './filter-service';
import {Observable} from 'rxjs';
import {Filter, Filters} from '../../shared/filter-schemas-and-types';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class RestFilterService implements FilterService {

  constructor(private http: HttpClient) {
  }

  filters(): Observable<Filters> {
    throw new Error('Method not implemented.');
  }

  addFilter(filter: Filter): void {
    throw new Error('Method not implemented.');
  }

  updateFilter(filter: Filter): void {
    throw new Error('Method not implemented.');
  }

  deleteFilter(id: string): void {
    throw new Error('Method not implemented.');
  }

}
