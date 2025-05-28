import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import(
        '../features/login/components/login-form/login-form.component'
      ).then((m) => m.LoginFormComponent),
  },

  {
    path: 'search',
    loadComponent: () =>
      import('../features/search-bar/search-bar.component').then(
        (m) => m.SearchBarComponent
      ),
    children: [
      {
        path: 'covoit',
        loadComponent: () =>
          import(
            '../features/carpooling/components/carpooling-list/carpooling-list.component'
          ).then((m) => m.CarpoolingListComponent),
      },
    ],
  },
];
