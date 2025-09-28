import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoaderService {

  private readonly loading = new BehaviorSubject<boolean>(false);
  readonly loading$ = this.loading.asObservable();

  public show() {
    this.loading.next(true);
  }

  public hide() {
    this.loading.next(false);
  }

}
