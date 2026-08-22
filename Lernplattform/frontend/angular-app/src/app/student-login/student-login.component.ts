import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { AppContextService, AuthenticatedAppContext } from '../core/app-context.service';

interface CsrfResponse { token: string; headerName: string; }

@Component({
  selector: 'app-student-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  template: `
    <main class="page-shell narrow">
      @if (mode() === 'login') {
        <section class="form-card">
          <p class="eyebrow">Schülerzugang</p>
          <h1>Anmelden</h1>
          <form [formGroup]="loginForm" (ngSubmit)="login()">
            <label>Benutzername<input formControlName="username" autocomplete="username"></label>
            <label>Passwort<input formControlName="password" type="password" autocomplete="current-password"></label>
            <button class="button primary" type="submit" [disabled]="loginForm.invalid || busy()">Anmelden</button>
          </form>
          @if (message()) { <p class="message error" role="alert">{{ message() }}</p> }
        </section>
      } @else {
        <section class="form-card">
          <p class="eyebrow">Erster Login</p>
          <h1>Passwort ändern</h1>
          <p class="lead">Das temporäre Passwort muss vor der Nutzung der Lernplattform geändert werden.</p>
          <form [formGroup]="passwordForm" (ngSubmit)="changePassword()">
            <label>Aktuelles Passwort<input formControlName="currentPassword" type="password" autocomplete="current-password"></label>
            <label>Neues Passwort<input formControlName="newPassword" type="password" autocomplete="new-password"></label>
            <label>Neues Passwort wiederholen<input formControlName="repeatPassword" type="password" autocomplete="new-password"></label>
            <button class="button primary" type="submit" [disabled]="passwordForm.invalid || busy()">Passwort ändern</button>
          </form>
          @if (message()) { <p class="message error" role="alert">{{ message() }}</p> }
        </section>
      }
    </main>
  `
})
export class StudentLoginComponent {
  private readonly http = inject(HttpClient);
  private readonly appContext = inject(AppContextService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);

  readonly mode = signal<'login' | 'password'>('login');
  readonly busy = signal(false);
  readonly message = signal('');
  private currentPassword = '';

  readonly loginForm = this.fb.nonNullable.group({ username: ['', Validators.required], password: ['', Validators.required] });
  readonly passwordForm = this.fb.nonNullable.group({
    currentPassword: ['', Validators.required],
    newPassword: ['', [Validators.required, Validators.minLength(12)]],
    repeatPassword: ['', [Validators.required, Validators.minLength(12)]]
  });

  async login(): Promise<void> {
    if (this.loginForm.invalid || this.busy()) return;
    this.busy.set(true); this.message.set('');
    const school = this.route.snapshot.paramMap.get('school') ?? '';
    const { username, password } = this.loginForm.getRawValue();
    try {
      await firstValueFrom(this.http.post(`/api/v1/schools/${encodeURIComponent(school)}/student-auth/login`, { username, password }, { withCredentials: true }));
      this.currentPassword = password;
      this.loginForm.controls.password.reset();
      const context = await firstValueFrom(this.appContext.load(school));
      if (context.capabilities.includes('CHANGE_OWN_PASSWORD')) {
        this.passwordForm.controls.currentPassword.setValue(password);
        this.mode.set('password');
      } else {
        await this.enter(context, school);
      }
    } catch {
      this.message.set('Anmeldung fehlgeschlagen. Benutzername, Passwort und Schule prüfen.');
    } finally { this.busy.set(false); }
  }

  async changePassword(): Promise<void> {
    if (this.passwordForm.invalid || this.busy()) return;
    const { currentPassword, newPassword, repeatPassword } = this.passwordForm.getRawValue();
    if (newPassword !== repeatPassword) { this.message.set('Die neuen Passwörter stimmen nicht überein.'); return; }
    this.busy.set(true); this.message.set('');
    try {
      const csrf = await firstValueFrom(this.http.get<CsrfResponse>('/api/v1/student-auth/csrf', { withCredentials: true }));
      const headers = new HttpHeaders().set(csrf.headerName, csrf.token);
      await firstValueFrom(this.http.post('/api/v1/student-auth/change-password', { currentPassword, newPassword }, { headers, withCredentials: true }));
      this.passwordForm.reset();
      this.mode.set('login');
      this.message.set('Passwort geändert. Bitte mit dem neuen Passwort erneut anmelden.');
    } catch {
      this.message.set('Das Passwort konnte nicht geändert werden.');
    } finally { this.busy.set(false); }
  }

  private async enter(context: AuthenticatedAppContext, school: string): Promise<void> {
    const first = context.navigation[0];
    await this.router.navigateByUrl(first?.route ?? `/${school}/lernen`);
  }
}
