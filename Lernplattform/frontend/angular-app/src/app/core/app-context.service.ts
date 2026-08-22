import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

export interface NavigationItem {
  id: string;
  label: string;
  route: string;
}

export interface SchoolContext {
  schoolId: string;
  schoolSlug: string;
  schoolName: string;
}

export interface AuthenticatedAppContext {
  account: {
    id: string;
    type: 'SYSTEM' | 'TEACHER' | 'STUDENT';
    displayName: string;
  };
  context: SchoolContext | null;
  availableContexts: SchoolContext[];
  capabilities: string[];
  navigation: NavigationItem[];
}

@Injectable({ providedIn: 'root' })
export class AppContextService {
  private readonly http = inject(HttpClient);

  load(schoolSlug?: string): Observable<AuthenticatedAppContext> {
    let params = new HttpParams();
    if (schoolSlug) params = params.set('school', schoolSlug);
    return this.http.get<AuthenticatedAppContext>('/api/v1/me', {
      params,
      withCredentials: true
    });
  }
}
