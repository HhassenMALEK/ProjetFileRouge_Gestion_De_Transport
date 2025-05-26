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

export interface GetPersonalVehicleParams {
  /** format: int64 */
  id: number;
}

export interface UpdatePersonalVehicleParams {
  /** format: int64 */
  id: number;
}

export interface DeletePersonalVehicleParams {
  /** format: int64 */
  id: number;
}

@Injectable()
export class PersonalVehicleService {
  constructor(private http: HttpClient) {}

  /** http://undefined/swagger/swagger-ui.html#!/personal-vehicle-controller/getPersonalVehicle */
  getPersonalVehicle(params: GetPersonalVehicleParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.get<void>(`/api/personal-vehicle/${pathParams.id}`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/personal-vehicle-controller/updatePersonalVehicle */
  updatePersonalVehicle(params: UpdatePersonalVehicleParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.put<void>(`/api/personal-vehicle/${pathParams.id}`, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/personal-vehicle-controller/deletePersonalVehicle */
  deletePersonalVehicle(params: DeletePersonalVehicleParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.delete<void>(`/api/personal-vehicle/${pathParams.id}`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/personal-vehicle-controller/createPersonalVehicle */
  createPersonalVehicle(): Observable<void> {
    return this.http.post<void>(`/api/personal-vehicle`, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/personal-vehicle-controller/getPersonalVehiclesByUserId */
  list(): Observable<void> {
    return this.http.get<void>(`/api/personal-vehicle/list`);
  }
}
