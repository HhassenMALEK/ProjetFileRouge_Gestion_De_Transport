import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('@features/login/components/login-form/login-form.component').then(
        (m) => m.LoginFormComponent
      ),
  },

  {
    path: 'search',
    loadComponent: () =>
      import('@features/search-bar/search-bar.component').then(
        (m) => m.SearchBarComponent
      ),
    children: [
      {
        path: 'covoit',
        loadComponent: () =>
          import(
            '@features/carpooling/components/carpooling-list/carpooling-list.component'
          ).then((m) => m.CarpoolingListComponent),
      },
    ],
  },
  {
    path: 'carpooling',
    loadComponent: () =>
      import(
        '@features/carpooling/pages/list-carpooling-organiser/list-carpooling-organiser.component'
      ).then((m) => m.ListCarpoolingOrganiserComponent),
  },
  {
    path: 'create-carpooling', // Ajouter cette route pour la création de covoiturage
    loadComponent: () =>
      import(
        '@features/carpooling/pages/create-carpooling/create-carpooling.component' // Remplacez par le chemin correct de votre composant
      ).then((m) => m.CreateCarpoolingComponent),
  },
  {
    path: 'serviceVehicle',
    loadComponent: () =>
      import(
        '@features/ServiceVehicle/components/service-vehicle-main/service-vehicle-main.component'
      ).then((m) => m.ServiceVehicleMainComponent),
  },
  {
    path: 'carpooling-reservation',
    loadComponent: () =>
      import(
        '@features/carpooling-reservations/components/carpooling-resa-list/carpooling-resa-list.component'
      ).then((m) => m.CarpoolingResaListComponent),
  },
  {
    path: 'carpooling-reservation/details/:id',
    loadComponent: () =>
      import(
        '@features/carpooling-reservations/components/carpooling-resa-detail/carpooling-resa-detail.component'
      ).then((m) => m.CarpoolingResaDetailComponent),
  },
];
