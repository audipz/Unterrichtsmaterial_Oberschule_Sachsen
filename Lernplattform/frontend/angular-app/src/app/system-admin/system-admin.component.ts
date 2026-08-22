import { Component, OnInit, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AppContextService, AuthenticatedAppContext, NavigationItem } from '../core/app-context.service';
import { UiModuleHostComponent } from '../core/ui-module-host.component';
import { SystemAdminApiService } from './system-admin-api.service';

@Component({
  selector: 'app-system-admin',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, UiModuleHostComponent],
  template: `
    <main class="page-shell admin-shell">
      <a class="back-link" routerLink="/">← Zur Startseite</a>

      @if (view() !== 'login' && navigation().length > 0) {
        <nav class="admin-nav" aria-label="Systemverwaltung">
          @for (item of navigation(); track item.id) {
            <button class="admin-nav-link" type="button" (click)="activate(item)">{{ item.label }}</button>
          }
        </nav>
      }

      @if (view() === 'login') {
        <section class="form-card admin-card compact-card">
          <p class="eyebrow">Systemverwaltung</p>
          <h1>Administrator anmelden</h1>
          <p class="lead">Dieser Zugang ist ausschließlich für die zentrale Verwaltung der Plattform vorgesehen.</p>
          <form [formGroup]="loginForm" (ngSubmit)="login()">
            <label>Benutzername<input formControlName="username" autocomplete="username"></label>
            <label>Passwort<input formControlName="password" type="password" autocomplete="current-password"></label>
            <button class="button primary" type="submit" [disabled]="loginForm.invalid || busy()">Anmelden</button>
            @if (message()) { <p class="message error" role="alert">{{ message() }}</p> }
          </form>
        </section>
      }

      @if (view() === 'password') {
        <section class="form-card admin-card compact-card">
          <p class="eyebrow">Erster Login</p>
          <h1>Passwort ändern</h1>
          <p class="lead">Das initiale Deployment-Passwort darf nicht für die weitere Administration verwendet werden.</p>
          <form [formGroup]="passwordForm" (ngSubmit)="changePassword()">
            <label>Aktuelles Passwort<input formControlName="currentPassword" type="password" autocomplete="current-password"></label>
            <label>Neues Passwort<input formControlName="newPassword" type="password" autocomplete="new-password"></label>
            <label>Neues Passwort wiederholen<input formControlName="repeatPassword" type="password" autocomplete="new-password"></label>
            <p class="privacy-note">Mindestens 16 Zeichen. Nach der Änderung ist eine erneute Anmeldung erforderlich.</p>
            <button class="button primary" type="submit" [disabled]="passwordForm.invalid || busy()">Passwort ändern</button>
            @if (message()) { <p class="message error" role="alert">{{ message() }}</p> }
          </form>
        </section>
      }

      @if (view() === 'module') {
        <section class="admin-header">
          <div><p class="eyebrow">Systemverwaltung</p><h1>{{ activeNavigation()?.label ?? 'Verwaltung' }}</h1></div>
          <button class="button" type="button" (click)="logout()" [disabled]="busy()">Abmelden</button>
        </section>
        @if (message()) { <p class="message error" role="alert">{{ message() }}</p> }
        @if (activeNavigation()?.moduleId; as moduleId) {
          <app-ui-module-host [moduleId]="moduleId" [context]="runtimeContext()" />
        }
      }
    </main>
  `
})
export class SystemAdminComponent implements OnInit {
  private readonly api = inject(SystemAdminApiService);
  private readonly appContext = inject(AppContextService);
  private readonly fb = inject(FormBuilder);

  readonly view = signal<'login' | 'password' | 'module'>('login');
  readonly busy = signal(false);
  readonly message = signal('');
  readonly navigation = signal<NavigationItem[]>([]);
  readonly activeNavigation = signal<NavigationItem | null>(null);
  readonly runtimeContext = signal<AuthenticatedAppContext | null>(null);

  readonly loginForm = this.fb.nonNullable.group({ username: ['', Validators.required], password: ['', Validators.required] });
  readonly passwordForm = this.fb.nonNullable.group({
    currentPassword: ['', Validators.required],
    newPassword: ['', [Validators.required, Validators.minLength(16)]],
    repeatPassword: ['', [Validators.required, Validators.minLength(16)]]
  });

  ngOnInit(): void { this.restoreSession(); }

  login(): void {
    if (this.loginForm.invalid || this.busy()) return;
    this.busy.set(true); this.message.set('');
    const { username, password } = this.loginForm.getRawValue();
    this.api.login(username, password).subscribe({
      next: () => {
        this.loginForm.controls.password.reset();
        this.passwordForm.controls.currentPassword.setValue(password);
        this.loadIdentity();
      },
      error: () => { this.busy.set(false); this.message.set('Anmeldung fehlgeschlagen. Benutzername oder Passwort prüfen.'); }
    });
  }

  changePassword(): void {
    if (this.passwordForm.invalid || this.busy()) return;
    const { currentPassword, newPassword, repeatPassword } = this.passwordForm.getRawValue();
    if (newPassword !== repeatPassword) { this.message.set('Die neuen Passwörter stimmen nicht überein.'); return; }
    this.busy.set(true); this.message.set('');
    this.api.changePassword(currentPassword, newPassword).subscribe({
      next: () => { this.passwordForm.reset(); this.resetToLogin(); },
      error: () => { this.busy.set(false); this.message.set('Das Passwort konnte nicht geändert werden.'); }
    });
  }

  activate(item: NavigationItem): void {
    if (!item.moduleId) return;
    this.activeNavigation.set(item);
    this.view.set('module');
  }

  logout(): void {
    if (this.busy()) return;
    this.busy.set(true);
    this.api.logout().subscribe({ next: () => this.resetToLogin(), error: () => this.resetToLogin() });
  }

  private restoreSession(): void {
    this.appContext.load().subscribe({ next: (context) => this.applyContext(context), error: () => this.resetToLogin() });
  }

  private loadIdentity(): void {
    this.appContext.load().subscribe({
      next: (context) => { this.busy.set(false); this.applyContext(context); },
      error: () => { this.busy.set(false); this.message.set('Die Berechtigungen konnten nicht geladen werden.'); }
    });
  }

  private applyContext(context: AuthenticatedAppContext): void {
    this.navigation.set(context.navigation);
    this.runtimeContext.set(context);
    if (context.capabilities.includes('CHANGE_OWN_PASSWORD')) {
      this.activeNavigation.set(null); this.view.set('password'); return;
    }
    const firstModule = context.navigation.find((item) => !!item.moduleId) ?? null;
    if (firstModule) { this.activeNavigation.set(firstModule); this.view.set('module'); return; }
    this.message.set('Für diesen Zugang sind keine Verwaltungsfunktionen freigeschaltet.');
  }

  private resetToLogin(): void {
    this.busy.set(false); this.navigation.set([]); this.activeNavigation.set(null);
    this.runtimeContext.set(null); this.view.set('login'); this.message.set('');
  }
}
