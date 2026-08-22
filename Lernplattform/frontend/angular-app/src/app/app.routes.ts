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
    path: 'registrierung-bestaetigen',
    loadComponent: () => import('./school-registration/registration-verification.component')
      .then((m) => m.RegistrationVerificationComponent)
  },
  {
    path: 'system-admin',
    loadComponent: () => import('./system-admin/system-admin.component').then((m) => m.SystemAdminComponent)
  },
  {
    path: ':school/:view',
    loadComponent: () => import('./shell/app-shell.component').then((m) => m.AppShellComponent)
  },
  {
    path: '**',
    redirectTo: ''
  }
];
