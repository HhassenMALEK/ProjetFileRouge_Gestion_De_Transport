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

export interface GetUserByIdParams {
  /** format: int64 */
  id: number;
}

export interface UpdateUserParams {
  /** format: int64 */
  id: number;
}

export interface DeleteUserParams {
  /** format: int64 */
  id: number;
}

@Injectable()
export class UserService {
  constructor(private http: HttpClient) {}

  /** http://undefined/swagger/swagger-ui.html#!/user-controller/getUserById */
  getUserById(params: GetUserByIdParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.get<void>(`/api/users/${pathParams.id}`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/user-controller/updateUser */
  updateUser(params: UpdateUserParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.put<void>(`/api/users/${pathParams.id}`, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/user-controller/deleteUser */
  deleteUser(params: DeleteUserParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.delete<void>(`/api/users/${pathParams.id}`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/user-controller/getAllUsers */
  all(): Observable<void> {
    return this.http.get<void>(`/api/users/all`);
  }
}
