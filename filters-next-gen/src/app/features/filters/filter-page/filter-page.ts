import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';
import {MatButton} from '@angular/material/button';
import {FilterList} from '../filter-list/filter-list';

@Component({
  selector: 'app-filter-page',
  imports: [
    RouterLink,
    MatButton,
    FilterList
  ],
  templateUrl: './filter-page.html',
  styleUrl: './filter-page.css'
})
export class FilterPage {

  onShowFilterAddModal() {

  }
}
