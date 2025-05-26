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

export interface GetSiteByIdParams {
  /** format: int64 */
  id: number;
}

export interface UpdateSiteParams {
  /** format: int64 */
  id: number;
}

export interface DeleteSiteParams {
  /** format: int64 */
  id: number;
}

@Injectable()
export class SiteService {
  constructor(private http: HttpClient) {}

  /** http://undefined/swagger/swagger-ui.html#!/site-controller/getSiteById */
  getSiteById(params: GetSiteByIdParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.get<void>(`/api/sites/${pathParams.id}`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/site-controller/updateSite */
  updateSite(params: UpdateSiteParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.put<void>(`/api/sites/${pathParams.id}`, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/site-controller/deleteSite */
  deleteSite(params: DeleteSiteParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.delete<void>(`/api/sites/${pathParams.id}`);
  }

  /** http://undefined/swagger/swagger-ui.html#!/site-controller/createSite */
  createSite(): Observable<void> {
    return this.http.post<void>(`/api/sites`, {});
  }

  /** http://undefined/swagger/swagger-ui.html#!/site-controller/getAllSites */
  list(): Observable<void> {
    return this.http.get<void>(`/api/sites/list`);
  }
}
