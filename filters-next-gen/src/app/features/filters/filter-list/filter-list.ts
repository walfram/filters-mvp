import {Component, inject, OnInit} from '@angular/core';
import {FilterService} from '../../../shared/services/filter-service';
import {AsyncPipe} from '@angular/common';
import {Filter} from '../types/filter';
import {Observable} from 'rxjs';
import {MatMiniFabButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {CriterionToStringPipe} from '../../../shared/pipes/criterion-to-string-pipe';

@Component({
  selector: 'app-filter-list',
  imports: [
    AsyncPipe,
    MatIcon,
    MatMiniFabButton,
    CriterionToStringPipe
  ],
  templateUrl: './filter-list.html',
  styleUrl: './filter-list.css'
})
export class FilterList implements OnInit {
  protected readonly filterService = inject(FilterService);
  protected filters$: Observable<Filter[]> | undefined;

  ngOnInit(): void {
    this.filters$ = this.filterService.filters();
  }

}
