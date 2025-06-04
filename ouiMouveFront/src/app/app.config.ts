import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { BASE_PATH } from './path';
import { routes } from './app.routes';
import {
  provideClientHydration,
  withEventReplay,
} from '@angular/platform-browser';

import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor } from '../shared/service/auth.interceptor';
import { Configuration } from '../service';
export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptor])),
    provideClientHydration(withEventReplay()),
    { provide: BASE_PATH, useValue: 'http://localhost:8087' },
    {
      provide: Configuration,
      useFactory: () => {
        const config = new Configuration({
          basePath: 'http://localhost:8087',
          withCredentials: false,
        });

        // Forcer explicitement la sélection JSON pour Accept
        const originalSelectHeaderAccept =
          config.selectHeaderAccept.bind(config);
        config.selectHeaderAccept = (accepts: string[]) => {
          const jsonType = accepts.find((type) =>
            type.includes('application/json')
          );
          return jsonType || 'application/json'; // Forcer JSON par défaut
        };

        // Forcer explicitement la sélection JSON pour Content-Type
        const originalSelectHeaderContentType =
          config.selectHeaderContentType.bind(config);
        config.selectHeaderContentType = (contentTypes: string[]) => {
          const jsonType = contentTypes.find((type) =>
            type.includes('application/json')
          );
          return jsonType || 'application/json'; // Forcer JSON par défaut
        };

        return config;
      },
    },
  ],
};
