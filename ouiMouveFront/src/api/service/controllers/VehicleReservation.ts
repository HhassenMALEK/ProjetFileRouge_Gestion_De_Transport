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

export interface GetReservationByIdParams {
  /** format: int64 */
  id: number;
}

export interface DeleteReservationParams {
  /** format: int64 */
  id: number;
}

export interface UpdateReservationParams {
  /** format: int64 */
  id: number;
}

export interface FilterParams {
  /** format: int64 */
  userId: number;
  /** Start date filter (optional, format: yyyy-MM-dd) */
  start?: string;
  /** Reservation status filter (optional) */
  status?: string;
}

export interface ListParams {
  /** format: int64 */
  vehicleId: number;
}

export interface UserParams {
  /** format: int64 */
  UserID: number;
}

@Injectable()
export class VehicleReservationService {
  constructor(private http: HttpClient) {}

  /**
   * Create a reservation
   * http://undefined/swagger/swagger-ui.html#!/vehicle-reservation-controller/createReservation
   */
  createReservation(): Observable<void> {
    return this.http.post<void>(`/api/reservation`, {});
  }

  /**
   * Get a reservation by its ID
   * http://undefined/swagger/swagger-ui.html#!/vehicle-reservation-controller/getReservationById
   */
  getReservationById(params: GetReservationByIdParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.get<void>(`/api/reservation/${pathParams.id}`);
  }

  /**
   * Delete a reservation by its ID
   * http://undefined/swagger/swagger-ui.html#!/vehicle-reservation-controller/deleteReservation
   */
  deleteReservation(params: DeleteReservationParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.delete<void>(`/api/reservation/${pathParams.id}`);
  }

  /**
   * Update a reservation by its ID
   * http://undefined/swagger/swagger-ui.html#!/vehicle-reservation-controller/updateReservation
   */
  updateReservation(params: UpdateReservationParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.patch<void>(`/api/reservation/${pathParams.id}`, {});
  }

  /**
   * Get all reservations for a user with optional filters
   * Retrieves reservations filtered by start date and/or status. Both filters are optional.
   * http://undefined/swagger/swagger-ui.html#!/vehicle-reservation-controller/getAllReservationsByUserWithFilters
   */
  filter(params: FilterParams): Observable<void> {
    const pathParams = {
      userId: params.userId,
    };
    const queryParamBase = {
      start: params.start,
      status: params.status,
    };

    let queryParams = new HttpParams();
    Object.entries(queryParamBase).forEach(([key, value]: [string, any]) => {
      if (value !== undefined) {
        if (typeof value === 'string') queryParams = queryParams.set(key, value);
        else if (Array.isArray(value)) value.forEach(v => queryParams = queryParams.append(key, v));
        else queryParams = queryParams.set(key, JSON.stringify(value));
      }
    });

    return this.http.get<void>(`/api/reservation/user/${pathParams.userId}/filter`, {params: queryParams});
  }

  /**
   * Get all reservations for a vehicle
   * http://undefined/swagger/swagger-ui.html#!/vehicle-reservation-controller/getAllReservationsByVehicle
   */
  list(params: ListParams): Observable<void> {
    const pathParams = {
      vehicleId: params.vehicleId,
    };
    return this.http.get<void>(`/api/reservation/list/${pathParams.vehicleId}`);
  }

  /**
   * Get all reservations for a user
   * http://undefined/swagger/swagger-ui.html#!/vehicle-reservation-controller/getAllReservationsByUser
   */
  user(params: UserParams): Observable<void> {
    const pathParams = {
      UserID: params.UserID,
    };
    return this.http.get<void>(`/api/reservation/list/user/${pathParams.UserID}`);
  }
}
