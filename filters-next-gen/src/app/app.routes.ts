import {Routes} from '@angular/router';
import {FilterPage} from './features/filters/filter-page/filter-page';
import {NewFilterPage} from './features/filters/new-filter-page/new-filter-page';
import {DebugDialog} from './debug/debug-dialog/debug-dialog';

export const routes: Routes = [{
  path: 'filters',
  component: FilterPage,
}, {
  path: 'filters/new',
  component: NewFilterPage
}, {
  path: 'debug/dialog',
  component: DebugDialog
}];
