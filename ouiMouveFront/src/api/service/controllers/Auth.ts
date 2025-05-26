/* tslint:disable:max-line-length */
/**
 * OuiMouve API
 * Ensemble des API de OuiMouve
 * M Basier, H Malek, J Brou, S Daudey
 * 1.0
 * undefined
 */

import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

@Injectable()
export class AuthService {
  constructor(private http: HttpClient) {}

  /** http://undefined/swagger/swagger-ui.html#!/auth-controller/register */
  register(): Observable<void> {
    return this.http.post<void>(`/api/auth/register`, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/auth-controller/login */
  login(): Observable<void> {
    return this.http.post<void>(`/api/auth/login`, {});
  }
}
