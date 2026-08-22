import { Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppContextService, AuthenticatedAppContext, NavigationItem, SchoolContext } from '../core/app-context.service';
import { UiModuleHostComponent } from '../core/ui-module-host.component';

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [UiModuleHostComponent],
  template: `
    <main class="page-shell app-shell">
      @if (loading()) {
        <p class="lead">Anwendung wird geladen …</p>
      } @else if (message()) {
        <p class="message error" role="alert">{{ message() }}</p>
      }

      @if (context(); as ctx) {
        <header class="app-shell-header">
          <div>
            <p class="eyebrow">{{ ctx.account.displayName }}</p>
            @if (ctx.context) {
              <h1>{{ ctx.context.schoolName }}</h1>
            } @else {
              <h1>Lernplattform</h1>
            }
          </div>

          @if (ctx.availableContexts.length > 1) {
            <label class="context-select">
              Schule
              <select [value]="ctx.context?.schoolSlug ?? ''" (change)="selectSchool($event)">
                <option value="" disabled>Schule auswählen</option>
                @for (school of ctx.availableContexts; track school.schoolId) {
                  <option [value]="school.schoolSlug">{{ school.schoolName }}</option>
                }
              </select>
            </label>
          }
        </header>

        @if (!ctx.context && ctx.availableContexts.length > 1) {
          <section class="info-card">
            <h2>Schule auswählen</h2>
            <p>Dieser Zugang ist mehreren Schulen zugeordnet. Bitte wähle zuerst den Arbeitskontext.</p>
          </section>
        } @else {
          @if (ctx.navigation.length > 0) {
            <nav class="app-nav" aria-label="Anwendungsnavigation">
              @for (item of ctx.navigation; track item.id) {
                <button type="button" class="admin-nav-link" [class.active]="activeNavigation()?.id === item.id" (click)="activate(item)">
                  {{ item.label }}
                </button>
              }
            </nav>
          }

          @if (activeNavigation()?.moduleId; as moduleId) {
            <app-ui-module-host
              [moduleId]="moduleId"
              [schoolSlug]="ctx.context?.schoolSlug"
              [context]="ctx" />
          } @else if (ctx.navigation.length > 0) {
            <section class="info-card">
              <p>Für diesen Menüpunkt ist noch kein UI-Modul hinterlegt.</p>
            </section>
          }
        }
      }
    </main>
  `
})
export class AppShellComponent implements OnInit {
  private readonly appContext = inject(AppContextService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  readonly loading = signal(true);
  readonly message = signal('');
  readonly context = signal<AuthenticatedAppContext | null>(null);
  readonly activeNavigation = signal<NavigationItem | null>(null);

  ngOnInit(): void {
    const requestedSchool = this.route.snapshot.paramMap.get('school') ?? undefined;
    this.load(requestedSchool);
  }

  activate(item: NavigationItem): void {
    this.activeNavigation.set(item);
    if (item.route && this.router.url !== item.route) {
      void this.router.navigateByUrl(item.route, { replaceUrl: false });
    }
  }

  selectSchool(event: Event): void {
    const schoolSlug = (event.target as HTMLSelectElement).value;
    if (!schoolSlug) return;
    this.load(schoolSlug);
  }

  private load(schoolSlug?: string): void {
    this.loading.set(true);
    this.message.set('');
    this.appContext.load(schoolSlug).subscribe({
      next: (context) => {
        this.context.set(context);
        this.loading.set(false);
        const active = this.findActiveNavigation(context.navigation) ?? context.navigation.find((item) => !!item.moduleId) ?? null;
        this.activeNavigation.set(active);
      },
      error: () => {
        this.loading.set(false);
        this.context.set(null);
        this.activeNavigation.set(null);
        this.message.set('Die Anwendung konnte nicht geladen werden. Bitte erneut anmelden.');
      }
    });
  }

  private findActiveNavigation(items: NavigationItem[]): NavigationItem | null {
    const current = this.router.url.split('?')[0];
    return items.find((item) => item.route === current) ?? null;
  }
}
