import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, switchMap } from 'rxjs';

export interface SystemAdminLoginResponse {
  mustChangePassword: boolean;
  expiresAt: string;
}

export interface NavigationItem {
  id: string;
  label: string;
  route: string;
}

export interface SystemAdminMe {
  accountId: string;
  accountType: 'SYSTEM';
  capabilities: string[];
  navigation: NavigationItem[];
}

export interface PendingSchoolRegistration {
  id: string;
  schoolName: string;
  schoolType: string;
  federalState: string;
  city: string;
  contactEmail: string;
  schoolWebsite?: string | null;
  submittedAt: string;
  emailVerifiedAt: string;
}

interface CsrfResponse {
  token: string;
  headerName: string;
}

@Injectable({ providedIn: 'root' })
export class SystemAdminApiService {
  private readonly http = inject(HttpClient);
  private readonly base = '/api/v1/system-admin';

  login(username: string, password: string): Observable<SystemAdminLoginResponse> {
    return this.http.post<SystemAdminLoginResponse>(`${this.base}/auth/login`, { username, password }, {
      withCredentials: true
    });
  }

  me(): Observable<SystemAdminMe> {
    return this.http.get<SystemAdminMe>(`${this.base}/me`, { withCredentials: true });
  }

  changePassword(currentPassword: string, newPassword: string): Observable<void> {
    return this.withCsrf((headers) => this.http.post<void>(`${this.base}/auth/change-password`, {
      currentPassword,
      newPassword
    }, { headers, withCredentials: true }));
  }

  logout(): Observable<void> {
    return this.withCsrf((headers) => this.http.post<void>(`${this.base}/auth/logout`, {}, {
      headers,
      withCredentials: true
    }));
  }

  pendingRegistrations(): Observable<PendingSchoolRegistration[]> {
    return this.http.get<PendingSchoolRegistration[]>(`${this.base}/school-registrations`, {
      withCredentials: true
    });
  }

  approve(requestId: string): Observable<{ schoolId: string; status: string }> {
    return this.withCsrf((headers) => this.http.post<{ schoolId: string; status: string }>(
      `${this.base}/school-registrations/${requestId}/approve`, {}, { headers, withCredentials: true }));
  }

  reject(requestId: string, reason: string): Observable<void> {
    return this.withCsrf((headers) => this.http.post<void>(
      `${this.base}/school-registrations/${requestId}/reject`, { reason }, { headers, withCredentials: true }));
  }

  private withCsrf<T>(request: (headers: HttpHeaders) => Observable<T>): Observable<T> {
    return this.http.get<CsrfResponse>(`${this.base}/auth/csrf`, { withCredentials: true }).pipe(
      switchMap((csrf) => request(new HttpHeaders().set(csrf.headerName, csrf.token)))
    );
  }
}
