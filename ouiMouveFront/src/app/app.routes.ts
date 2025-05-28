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
    path: 'home',
    loadComponent: () =>
      import('./components/layout/layout.component').then(
        (m) => m.LayoutComponent
      ),
    children: [
      {
        path: 'search',
        loadComponent: () =>
          import(
            '../features/search-bar/components/search-covoit/search-covoit.component'
          ).then((m) => m.SearchCovoitComponent),
      },
    ],
  },
];
