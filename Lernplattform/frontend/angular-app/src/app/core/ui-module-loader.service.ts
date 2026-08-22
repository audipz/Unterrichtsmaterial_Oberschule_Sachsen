import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';

export interface ResolvedUiModule {
  moduleId: string;
  version: string;
  artifactUrl: string;
  integrity: string;
}

export interface UiRuntimeModule {
  mount(host: HTMLElement, context: unknown): void | Promise<void>;
  unmount?(host: HTMLElement): void | Promise<void>;
}

declare global {
  interface Window {
    __LP_UI_MODULES__?: Record<string, UiRuntimeModule>;
  }
}

@Injectable({ providedIn: 'root' })
export class UiModuleLoaderService {
  private readonly http = inject(HttpClient);
  private readonly loadedScripts = new Map<string, Promise<UiRuntimeModule>>();

  async load(moduleId: string, schoolSlug?: string): Promise<UiRuntimeModule> {
    const cached = window.__LP_UI_MODULES__?.[moduleId];
    if (cached) return cached;

    const running = this.loadedScripts.get(moduleId);
    if (running) return running;

    const promise = this.resolve(moduleId, schoolSlug).then((resolved) => this.loadScript(resolved));
    this.loadedScripts.set(moduleId, promise);
    try {
      return await promise;
    } catch (error) {
      this.loadedScripts.delete(moduleId);
      throw error;
    }
  }

  private async resolve(moduleId: string, schoolSlug?: string): Promise<ResolvedUiModule> {
    let params = new HttpParams();
    if (schoolSlug) params = params.set('school', schoolSlug);
    return firstValueFrom(this.http.get<ResolvedUiModule>(`/api/v1/ui-modules/${moduleId}`, {
      params,
      withCredentials: true
    }));
  }

  private loadScript(module: ResolvedUiModule): Promise<UiRuntimeModule> {
    return new Promise((resolve, reject) => {
      const script = document.createElement('script');
      script.type = 'module';
      script.src = module.artifactUrl;
      script.dataset['moduleId'] = module.moduleId;
      if (module.integrity) {
        script.integrity = module.integrity;
        script.crossOrigin = 'anonymous';
      }
      script.onload = () => {
        const registered = window.__LP_UI_MODULES__?.[module.moduleId];
        if (registered) {
          resolve(registered);
        } else {
          reject(new Error('Runtime module did not register itself.'));
        }
      };
      script.onerror = () => reject(new Error('Runtime module could not be loaded.'));
      document.head.appendChild(script);
    });
  }
}
