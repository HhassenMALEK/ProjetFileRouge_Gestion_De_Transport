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

export interface GetAdressByIdParams {
  /** format: int64 */
  id: number;
}

export interface DeleteAdressParams {
  /** format: int64 */
  id: number;
}

export interface UpdateAdressParams {
  /** format: int64 */
  id: number;
}

export interface GetAdressByLatAndLongParams {
  /** format: float */
  latX: number;
  /** format: float */
  longY: number;
}

export interface GetAdressByLabelAndCityParams {
  label: string;
  city: string;
}

@Injectable()
export class AdressService {
  constructor(private http: HttpClient) {}

  /** http://undefined/swagger/swagger-ui.html#!/adress-controller/createAdress */
  createAdress(): Observable<void> {
    return this.http.post<void>(`/api/adress `, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/adress-controller/getAdressById */
  getAdressById(params: GetAdressByIdParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.get<void>(`/api/adress /${pathParams.id}`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/adress-controller/deleteAdress */
  deleteAdress(params: DeleteAdressParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.delete<void>(`/api/adress /${pathParams.id}`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/adress-controller/updateAdress */
  updateAdress(params: UpdateAdressParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.patch<void>(`/api/adress /${pathParams.id}`, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/adress-controller/getAdressByLatAndLong */
  getAdressByLatAndLong(params: GetAdressByLatAndLongParams): Observable<void> {
    const pathParams = {
      latX: params.latX,
      longY: params.longY,
    };
    return this.http.get<void>(`/api/adress /${pathParams.latX}/${pathParams.longY}`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/adress-controller/getAdressByLabelAndCity */
  getAdressByLabelAndCity(params: GetAdressByLabelAndCityParams): Observable<void> {
    const pathParams = {
      label: params.label,
      city: params.city,
    };
    return this.http.get<void>(`/api/adress /${pathParams.label}/${pathParams.city}`);
  }
}
