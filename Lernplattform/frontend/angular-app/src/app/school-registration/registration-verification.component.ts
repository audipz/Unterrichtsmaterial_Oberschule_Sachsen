import { HttpClient } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  selector: 'app-registration-verification',
  standalone: true,
  imports: [RouterLink],
  template: `
    <main class="page-shell narrow">
      <section class="form-card">
        <p class="eyebrow">Schulregistrierung</p>
        <h1>E-Mail-Adresse bestätigen</h1>

        @if (!token()) {
          <p class="message error" role="alert">Der Bestätigungslink ist unvollständig.</p>
        } @else if (status() === 'idle') {
          <p class="lead">Bestätige jetzt, dass du Zugriff auf die angegebene Kontaktadresse hast. Der Link ist höchstens 24 Stunden gültig.</p>
          <button class="button primary" type="button" (click)="verify()">E-Mail-Adresse bestätigen</button>
        } @else if (status() === 'sending') {
          <p class="lead">Bestätigung wird geprüft …</p>
        } @else if (status() === 'success') {
          <p class="message success" role="status">Die E-Mail-Adresse wurde bestätigt. Der Antrag wartet jetzt auf die manuelle Prüfung.</p>
          <a class="button primary" routerLink="/">Zur Startseite</a>
        } @else {
          <p class="message error" role="alert">Der Link ist ungültig, bereits verwendet oder älter als 24 Stunden.</p>
          <a class="button" routerLink="/schule-registrieren">Neuen Antrag stellen</a>
        }
      </section>
    </main>
  `
})
export class RegistrationVerificationComponent {
  private readonly http = inject(HttpClient);
  private readonly route = inject(ActivatedRoute);

  readonly token = signal(this.route.snapshot.queryParamMap.get('token') ?? '');
  readonly status = signal<'idle' | 'sending' | 'success' | 'error'>('idle');

  verify(): void {
    if (!this.token() || this.status() !== 'idle') {
      return;
    }
    this.status.set('sending');
    this.http.post('/api/v1/public/school-registrations/verify', { token: this.token() }).subscribe({
      next: () => {
        this.token.set('');
        this.status.set('success');
      },
      error: () => {
        this.token.set('');
        this.status.set('error');
      }
    });
  }
}
