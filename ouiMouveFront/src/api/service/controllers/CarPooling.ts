/* tslint:disable:max-line-length */
/**
 * OuiMouve API
 * Ensemble des API de OuiMouve
 * M Basier, H Malek, J Brou, S Daudey
 * 1.0
 * undefined
 */

import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

export interface GetByIdParams {
  /** format: int64 */
  id: number;
}

export interface DeleteCarPoolingParams {
  /** format: int64 */
  id: number;
}

export interface UpdateCarPoolingParams {
  /** format: int64 */
  id: number;
}

export interface FilterParams {
  /** format: int64 */
  organizerId?: number;
  status?: StatusFilterParamsEnum;
  /** format: date-time */
  startDate?: string;
  /** format: date-time */
  endDate?: string;
  /** format: int64 */
  vehicleId?: number;
}

export type StatusFilterParamsEnum =
  'IN_PROGRESS' |
  'FINISHED' |
  'CANCELLED' |
  'BOOKING_OPEN' |
  'BOOKING_FULL';

@Injectable()
export class CarPoolingService {
  constructor(private http: HttpClient) {}

  /** http://undefined/swagger/swagger-ui.html#!/car-pooling-controller/createCarPooling */
  createCarPooling(): Observable<void> {
    return this.http.post<void>(`/api/carpooling`, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/car-pooling-controller/getById */
  getById(params: GetByIdParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.get<void>(`/api/carpooling/${pathParams.id}`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/car-pooling-controller/deleteCarPooling */
  deleteCarPooling(params: DeleteCarPoolingParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.delete<void>(`/api/carpooling/${pathParams.id}`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/car-pooling-controller/updateCarPooling */
  updateCarPooling(params: UpdateCarPoolingParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.patch<void>(`/api/carpooling/${pathParams.id}`, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/car-pooling-controller/getAll */
  list(): Observable<void> {
    return this.http.get<void>(`/api/carpooling/list`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/car-pooling-controller/filterByCriteria */
  filter(params: FilterParams): Observable<void> {
    const queryParamBase = {
      organizerId: params.organizerId,
      status: params.status,
      startDate: params.startDate,
      endDate: params.endDate,
      vehicleId: params.vehicleId,
    };

    let queryParams = new HttpParams();
    Object.entries(queryParamBase).forEach(([key, value]: [string, any]) => {
      if (value !== undefined) {
        if (typeof value === 'string') queryParams = queryParams.set(key, value);
        else if (Array.isArray(value)) value.forEach(v => queryParams = queryParams.append(key, v));
        else queryParams = queryParams.set(key, JSON.stringify(value));
      }
    });

    return this.http.get<void>(`/api/carpooling/filter`, {params: queryParams});
  }
}
