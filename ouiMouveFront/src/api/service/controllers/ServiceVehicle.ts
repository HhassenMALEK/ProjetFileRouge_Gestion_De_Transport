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

export interface GetServiceVehicleParams {
  /** format: int64 */
  id: number;
}

export interface UpdateServiceVehicleParams {
  /** format: int64 */
  id: number;
}

export interface DeleteServiceVehicleParams {
  /** format: int64 */
  id: number;
}

@Injectable()
export class ServiceVehicleService {
  constructor(private http: HttpClient) {}

  /**
   * Get a service vehicle by its ID
   * http://undefined/swagger/swagger-ui.html#!/service-vehicle-controller/getServiceVehicle
   */
  getServiceVehicle(params: GetServiceVehicleParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.get<void>(`/api/service-vehicle/${pathParams.id}`);
  }

  /**
   * Update an existing service vehicle
   * http://undefined/swagger/swagger-ui.html#!/service-vehicle-controller/updateServiceVehicle
   */
  updateServiceVehicle(params: UpdateServiceVehicleParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.put<void>(`/api/service-vehicle/${pathParams.id}`, {});
  }

  /**
   * Delete a service vehicle
   * http://undefined/swagger/swagger-ui.html#!/service-vehicle-controller/deleteServiceVehicle
   */
  deleteServiceVehicle(params: DeleteServiceVehicleParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.delete<void>(`/api/service-vehicle/${pathParams.id}`);
  }

  /**
   * Create a new service vehicle
   * http://undefined/swagger/swagger-ui.html#!/service-vehicle-controller/createServiceVehicle
   */
  createServiceVehicle(): Observable<void> {
    return this.http.post<void>(`/api/service-vehicle`, {});
  }
}
