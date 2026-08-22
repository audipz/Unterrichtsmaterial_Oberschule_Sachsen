import { Component, OnInit, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AppContextService, NavigationItem } from '../core/app-context.service';
import { PendingSchoolRegistration, SystemAdminApiService } from './system-admin-api.service';

@Component({
  selector: 'app-system-admin',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  template: `
    <main class="page-shell admin-shell">
      <a class="back-link" routerLink="/">← Zur Startseite</a>

      @if (view() !== 'login' && navigation().length > 0) {
        <nav class="admin-nav" aria-label="Systemverwaltung">
          @for (item of navigation(); track item.id) {
            <a class="admin-nav-link" [routerLink]="item.route">{{ item.label }}</a>
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

      @if (view() === 'review') {
        <section class="admin-header">
          <div>
            <p class="eyebrow">Systemverwaltung</p>
            <h1>Schulregistrierungen</h1>
            <p class="lead">Hier erscheinen nur Anträge mit bestätigter Kontakt-E-Mail.</p>
          </div>
          <button class="button" type="button" (click)="logout()" [disabled]="busy()">Abmelden</button>
        </section>

        @if (message()) { <p class="message error" role="alert">{{ message() }}</p> }
        @if (loading()) { <p class="lead">Anträge werden geladen …</p> }

        @if (!loading() && registrations().length === 0) {
          <section class="info-card empty-state">
            <h2>Keine offenen Anträge</h2>
            <p>Aktuell gibt es keine bestätigten Schulregistrierungen zur Prüfung.</p>
          </section>
        }

        <section class="registration-list">
          @for (registration of registrations(); track registration.id) {
            <article class="info-card registration-card">
              <div class="registration-heading">
                <div>
                  <p class="eyebrow">{{ registration.schoolType }} · {{ registration.federalState }}</p>
                  <h2>{{ registration.schoolName }}</h2>
                </div>
                <span class="status-badge">E-Mail bestätigt</span>
              </div>

              <dl class="registration-details">
                <div><dt>Ort</dt><dd>{{ registration.city }}</dd></div>
                <div><dt>Kontakt</dt><dd>{{ registration.contactEmail }}</dd></div>
                <div><dt>Eingereicht</dt><dd>{{ formatDate(registration.submittedAt) }}</dd></div>
                <div><dt>Bestätigt</dt><dd>{{ formatDate(registration.emailVerifiedAt) }}</dd></div>
              </dl>

              @if (registration.schoolWebsite) {
                <p><a [href]="registration.schoolWebsite" target="_blank" rel="noopener noreferrer">Schulwebseite öffnen</a></p>
              }

              <div class="review-actions">
                <button class="button primary" type="button" (click)="approve(registration)" [disabled]="busy()">Freigeben</button>
                <button class="button" type="button" (click)="toggleReject(registration.id)" [disabled]="busy()">Ablehnen</button>
              </div>

              @if (rejectingId() === registration.id) {
                <form class="reject-form" [formGroup]="rejectForm" (ngSubmit)="reject(registration)">
                  <label>Ablehnungsgrund<textarea formControlName="reason" rows="4" maxlength="1000"></textarea></label>
                  <div class="review-actions">
                    <button class="button" type="button" (click)="rejectingId.set(null)">Abbrechen</button>
                    <button class="button primary" type="submit" [disabled]="rejectForm.invalid || busy()">Ablehnung bestätigen</button>
                  </div>
                </form>
              }
            </article>
          }
        </section>
      }
    </main>
  `
})
export class SystemAdminComponent implements OnInit {
  private readonly api = inject(SystemAdminApiService);
  private readonly appContext = inject(AppContextService);
  private readonly fb = inject(FormBuilder);

  readonly view = signal<'login' | 'password' | 'review'>('login');
  readonly busy = signal(false);
  readonly loading = signal(false);
  readonly message = signal('');
  readonly registrations = signal<PendingSchoolRegistration[]>([]);
  readonly navigation = signal<NavigationItem[]>([]);
  readonly rejectingId = signal<string | null>(null);

  readonly loginForm = this.fb.nonNullable.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  readonly passwordForm = this.fb.nonNullable.group({
    currentPassword: ['', Validators.required],
    newPassword: ['', [Validators.required, Validators.minLength(16)]],
    repeatPassword: ['', [Validators.required, Validators.minLength(16)]]
  });

  readonly rejectForm = this.fb.nonNullable.group({
    reason: ['', [Validators.required, Validators.maxLength(1000)]]
  });

  ngOnInit(): void {
    this.restoreSession();
  }

  login(): void {
    if (this.loginForm.invalid || this.busy()) return;
    this.busy.set(true);
    this.message.set('');
    const { username, password } = this.loginForm.getRawValue();
    this.api.login(username, password).subscribe({
      next: () => {
        this.loginForm.controls.password.reset();
        this.passwordForm.controls.currentPassword.setValue(password);
        this.loadIdentity();
      },
      error: () => {
        this.busy.set(false);
        this.message.set('Anmeldung fehlgeschlagen. Benutzername oder Passwort prüfen.');
      }
    });
  }

  changePassword(): void {
    if (this.passwordForm.invalid || this.busy()) return;
    const { currentPassword, newPassword, repeatPassword } = this.passwordForm.getRawValue();
    if (newPassword !== repeatPassword) {
      this.message.set('Die neuen Passwörter stimmen nicht überein.');
      return;
    }
    this.busy.set(true);
    this.message.set('');
    this.api.changePassword(currentPassword, newPassword).subscribe({
      next: () => {
        this.passwordForm.reset();
        this.resetToLogin();
      },
      error: () => {
        this.busy.set(false);
        this.message.set('Das Passwort konnte nicht geändert werden.');
      }
    });
  }

  approve(registration: PendingSchoolRegistration): void {
    if (this.busy()) return;
    this.busy.set(true);
    this.message.set('');
    this.api.approve(registration.id).subscribe({
      next: () => {
        this.busy.set(false);
        this.registrations.update((items) => items.filter((item) => item.id !== registration.id));
      },
      error: () => {
        this.busy.set(false);
        this.message.set(`„${registration.schoolName}“ konnte nicht freigegeben werden.`);
      }
    });
  }

  toggleReject(id: string): void {
    this.rejectForm.reset();
    this.rejectingId.set(this.rejectingId() === id ? null : id);
  }

  reject(registration: PendingSchoolRegistration): void {
    if (this.rejectForm.invalid || this.busy()) return;
    this.busy.set(true);
    this.message.set('');
    this.api.reject(registration.id, this.rejectForm.getRawValue().reason).subscribe({
      next: () => {
        this.busy.set(false);
        this.rejectingId.set(null);
        this.registrations.update((items) => items.filter((item) => item.id !== registration.id));
      },
      error: () => {
        this.busy.set(false);
        this.message.set(`„${registration.schoolName}“ konnte nicht abgelehnt werden.`);
      }
    });
  }

  logout(): void {
    if (this.busy()) return;
    this.busy.set(true);
    this.api.logout().subscribe({
      next: () => this.resetToLogin(),
      error: () => this.resetToLogin()
    });
  }

  formatDate(value: string): string {
    return new Intl.DateTimeFormat('de-DE', { dateStyle: 'medium', timeStyle: 'short' }).format(new Date(value));
  }

  private restoreSession(): void {
    this.loading.set(true);
    this.appContext.load().subscribe({
      next: (context) => {
        this.navigation.set(context.navigation);
        this.loading.set(false);
        if (context.capabilities.includes('CHANGE_OWN_PASSWORD')) {
          this.view.set('password');
        } else if (context.capabilities.includes('SCHOOL_REGISTRATION_REVIEW')) {
          this.view.set('review');
          this.loadRegistrations();
        }
      },
      error: () => {
        this.loading.set(false);
        this.view.set('login');
      }
    });
  }

  private loadIdentity(): void {
    this.appContext.load().subscribe({
      next: (context) => {
        this.busy.set(false);
        this.navigation.set(context.navigation);
        if (context.capabilities.includes('CHANGE_OWN_PASSWORD')) {
          this.view.set('password');
          return;
        }
        if (context.capabilities.includes('SCHOOL_REGISTRATION_REVIEW')) {
          this.view.set('review');
          this.loadRegistrations();
          return;
        }
        this.message.set('Für diesen Zugang sind keine Verwaltungsfunktionen freigeschaltet.');
      },
      error: () => {
        this.busy.set(false);
        this.message.set('Die Berechtigungen konnten nicht geladen werden.');
      }
    });
  }

  private loadRegistrations(): void {
    this.loading.set(true);
    this.api.pendingRegistrations().subscribe({
      next: (items) => {
        this.registrations.set(items);
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.message.set('Die Registrierungsanträge konnten nicht geladen werden.');
      }
    });
  }

  private resetToLogin(): void {
    this.busy.set(false);
    this.loading.set(false);
    this.registrations.set([]);
    this.navigation.set([]);
    this.view.set('login');
    this.message.set('');
  }
}
