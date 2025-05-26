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

export interface GetReparationByIdParams {
  /** format: int64 */
  id: number;
}

export interface DeleteReparationParams {
  /** format: int64 */
  id: number;
}

export interface UpdateReparationParams {
  /** format: int64 */
  id: number;
}

export interface ListParams {
  /** format: int64 */
  vehicleId: number;
}

@Injectable()
export class ReparationService {
  constructor(private http: HttpClient) {}

  /** http://undefined/swagger/swagger-ui.html#!/reparation-controller/createReparation */
  createReparation(): Observable<void> {
    return this.http.post<void>(`/api/reparation`, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/reparation-controller/getReparationById */
  getReparationById(params: GetReparationByIdParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.get<void>(`/api/reparation/${pathParams.id}`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/reparation-controller/deleteReparation */
  deleteReparation(params: DeleteReparationParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.delete<void>(`/api/reparation/${pathParams.id}`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/reparation-controller/updateReparation */
  updateReparation(params: UpdateReparationParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.patch<void>(`/api/reparation/${pathParams.id}`, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/reparation-controller/getAllReparations */
  list(params: ListParams): Observable<void> {
    const pathParams = {
      vehicleId: params.vehicleId,
    };
    return this.http.get<void>(`/api/reparation/list/${pathParams.vehicleId}`);
  }
}
