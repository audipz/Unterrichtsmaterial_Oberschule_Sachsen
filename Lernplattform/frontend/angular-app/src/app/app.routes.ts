import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./home/home.component').then((m) => m.HomeComponent)
  },
  {
    path: 'schule-registrieren',
    loadComponent: () => import('./school-registration/school-registration.component').then((m) => m.SchoolRegistrationComponent)
  },
  {
    path: '**',
    redirectTo: ''
  }
];
