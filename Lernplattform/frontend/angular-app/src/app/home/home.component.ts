import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink],
  template: `
    <main class="page-shell">
      <section class="hero">
        <p class="eyebrow">Lernplattform Informatik</p>
        <h1>Selbstständig lernen. Gemeinsam begleiten.</h1>
        <p class="lead">Übungen und Arbeitsblätter für Schülerinnen und Schüler sowie Werkzeuge für Lehrkräfte und Schulen.</p>
        <div class="actions">
          <a class="button primary" href="/login">Anmelden</a>
          <a class="button secondary" routerLink="/schule-registrieren">Schule registrieren</a>
        </div>
      </section>
      <section class="info-card" aria-labelledby="school-title">
        <h2 id="school-title">Ihre Schule ist noch nicht dabei?</h2>
        <p>Eine Registrierung erzeugt zunächst nur einen Antrag. Die Schule wird erst nach manueller Prüfung freigeschaltet.</p>
      </section>
    </main>
  `
})
export class HomeComponent {}
