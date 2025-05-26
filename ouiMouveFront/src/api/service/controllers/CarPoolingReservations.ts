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

export interface SubscribeParams {
  /** format: int64 */
  resId: number;
}

export interface CancelParams {
  /** format: int64 */
  resId: number;
}

export interface GetCarPoolingReservationParams {
  /** format: int64 */
  id: number;
}

@Injectable()
export class CarPoolingReservationsService {
  constructor(private http: HttpClient) {}

  /** http://undefined/swagger/swagger-ui.html#!/car-pooling-reservations-controller/subscribeToCarPooling */
  subscribe(params: SubscribeParams): Observable<void> {
    const pathParams = {
      resId: params.resId,
    };
    return this.http.put<void>(`/api/carpooling-reservations/subscribe/${pathParams.resId}`, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/car-pooling-reservations-controller/cancelReservation */
  cancel(params: CancelParams): Observable<void> {
    const pathParams = {
      resId: params.resId,
    };
    return this.http.put<void>(`/api/carpooling-reservations/cancel/${pathParams.resId}`, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/car-pooling-reservations-controller/createCarPoolingReservation */
  createCarPoolingReservation(): Observable<void> {
    return this.http.post<void>(`/api/carpooling-reservations`, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/car-pooling-reservations-controller/getCarPoolingReservation */
  getCarPoolingReservation(params: GetCarPoolingReservationParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.get<void>(`/api/carpooling-reservations/${pathParams.id}`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/car-pooling-reservations-controller/getAllCarPoolingReservations */
  list(): Observable<void> {
    return this.http.get<void>(`/api/carpooling-reservations/list`);
  }
}
