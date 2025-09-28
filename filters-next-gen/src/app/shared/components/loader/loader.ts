import {Component, inject} from '@angular/core';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {LoaderService} from '../../services/loader-service';
import {AsyncPipe} from '@angular/common';

@Component({
  selector: 'app-loader',
  imports: [
    MatProgressSpinner,
    AsyncPipe
  ],
  templateUrl: './loader.html',
  styleUrl: './loader.css'
})
export class Loader {

  private readonly loaderService = inject(LoaderService);
  loading$ = this.loaderService.loading$;

}
