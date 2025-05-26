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

export interface GetModelByIdParams {
  /** format: int64 */
  id: number;
}

export interface DeleteModelParams {
  /** format: int64 */
  id: number;
}

export interface UpdateModelParams {
  /** format: int64 */
  id: number;
}

export interface CategoryParams {
  category: CategoryCategoryParamsEnum;
}

export type CategoryCategoryParamsEnum =
  'MICRO_URBANE' |
  'MINI_CITADINE' |
  'CITADINE_POLYVALENTE' |
  'COMPACTE' |
  'BERLINE_TAILLE_S' |
  'BERLINE_TAILLE_M' |
  'BERLINE_TAILLE_L' |
  'SUV_TOUT_TERRAIN_ET_PICKUP';

@Injectable()
export class ModelService {
  constructor(private http: HttpClient) {}

  /**
   * Create a model
   * http://undefined/swagger/swagger-ui.html#!/model-controller/createModel
   */
  createModel(): Observable<void> {
    return this.http.post<void>(`/api/model`, {});
  }

  /**
   * Get a model by its ID
   * http://undefined/swagger/swagger-ui.html#!/model-controller/getModelById
   */
  getModelById(params: GetModelByIdParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.get<void>(`/api/model/${pathParams.id}`);
  }

  /**
   * Delete a model by its ID
   * http://undefined/swagger/swagger-ui.html#!/model-controller/deleteModel
   */
  deleteModel(params: DeleteModelParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.delete<void>(`/api/model/${pathParams.id}`);
  }

  /**
   * Update a model by its ID
   * http://undefined/swagger/swagger-ui.html#!/model-controller/updateModel
   */
  updateModel(params: UpdateModelParams): Observable<void> {
    const pathParams = {
      id: params.id,
    };
    return this.http.patch<void>(`/api/model/${pathParams.id}`, {});
  }

  /**
   * Get all models
   * http://undefined/swagger/swagger-ui.html#!/model-controller/getAllModels
   */
  list(): Observable<void> {
    return this.http.get<void>(`/api/model/list`);
  }

  /**
   * Get all models by category
   * http://undefined/swagger/swagger-ui.html#!/model-controller/getAllModelsByCategory
   */
  category(params: CategoryParams): Observable<void> {
    const pathParams = {
      category: params.category,
    };
    return this.http.get<void>(`/api/model/category/${pathParams.category}`);
  }
}
