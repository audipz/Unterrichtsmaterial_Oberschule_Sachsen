import { HttpClient } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';

interface SchoolRegistrationRequest {
  schoolName: string;
  schoolType: string;
  federalState: string;
  city: string;
  contactEmail: string;
  schoolWebsite?: string;
  website: string;
}

@Component({
  selector: 'app-school-registration',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  template: `
    <main class="page-shell narrow">
      <a class="back-link" routerLink="/">← Zur Startseite</a>
      <section class="form-card">
        <p class="eyebrow">Schule registrieren</p>
        <h1>Registrierungsantrag stellen</h1>
        <p class="lead">Der Antrag wird nach Bestätigung der Kontaktadresse manuell geprüft. Erst danach kann eine Schule freigeschaltet werden.</p>

        <form [formGroup]="form" (ngSubmit)="submit()" novalidate>
          <label>Schulname<input formControlName="schoolName" autocomplete="organization"></label>
          <label>Schulart
            <select formControlName="schoolType">
              <option value="OBERSCHULE">Oberschule</option>
              <option value="GYMNASIUM">Gymnasium</option>
              <option value="FOERDERSCHULE">Förderschule</option>
              <option value="OTHER">Andere</option>
            </select>
          </label>
          <label>Bundesland<input formControlName="federalState" autocomplete="address-level1"></label>
          <label>Ort<input formControlName="city" autocomplete="address-level2"></label>
          <label>Dienstliche Kontakt-E-Mail<input formControlName="contactEmail" type="email" autocomplete="email"></label>
          <label>Webseite der Schule <span>(optional)</span><input formControlName="schoolWebsite" type="url" autocomplete="url"></label>

          <div class="trap" aria-hidden="true">
            <label>Website<input formControlName="website" tabindex="-1" autocomplete="off"></label>
          </div>

          <p class="privacy-note">Es werden nur die für Prüfung und Rückmeldung erforderlichen Angaben verarbeitet. Ein Antrag erzeugt noch kein Benutzerkonto.</p>

          <button class="button primary" type="submit" [disabled]="form.invalid || status() === 'sending'">
            @if (status() === 'sending') { Antrag wird gesendet … } @else { Antrag absenden }
          </button>

          @if (status() === 'success') {
            <p class="message success" role="status">Der Antrag wurde angenommen. Bitte bestätige als Nächstes die Kontaktadresse.</p>
          }
          @if (status() === 'error') {
            <p class="message error" role="alert">Der Antrag konnte nicht übermittelt werden. Bitte später erneut versuchen.</p>
          }
        </form>
      </section>
    </main>
  `
})
export class SchoolRegistrationComponent {
  private readonly http = inject(HttpClient);
  private readonly fb = inject(FormBuilder);

  readonly status = signal<'idle' | 'sending' | 'success' | 'error'>('idle');

  readonly form = this.fb.nonNullable.group({
    schoolName: ['', [Validators.required, Validators.maxLength(200)]],
    schoolType: ['OBERSCHULE', Validators.required],
    federalState: ['SACHSEN', [Validators.required, Validators.maxLength(60)]],
    city: ['', [Validators.required, Validators.maxLength(120)]],
    contactEmail: ['', [Validators.required, Validators.email, Validators.maxLength(320)]],
    schoolWebsite: ['', Validators.maxLength(500)],
    website: ['']
  });

  submit(): void {
    if (this.form.invalid || this.status() === 'sending') {
      return;
    }

    this.status.set('sending');
    const request: SchoolRegistrationRequest = this.form.getRawValue();

    this.http.post<void>('/api/v1/public/school-registrations', request).subscribe({
      next: () => {
        this.status.set('success');
        this.form.disable();
      },
      error: () => this.status.set('error')
    });
  }
}
