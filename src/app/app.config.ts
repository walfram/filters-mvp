import {ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {FILTER_SERVICE} from './filters/services/filter-service';
import {LocalFilterService} from './filters/services/local-filter.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    {
      provide: FILTER_SERVICE,
      useClass: LocalFilterService
    }
  ]
};
