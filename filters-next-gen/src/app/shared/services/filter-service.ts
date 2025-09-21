import { Injectable } from '@angular/core';
import {Filter} from '../../features/filters/types/filter';
import {SAMPLE_FILTERS} from '../../mocks/sample-filters';
import {Observable, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FilterService {

  filters(): Observable<Filter[]> {
    return of(SAMPLE_FILTERS);
  }

}
