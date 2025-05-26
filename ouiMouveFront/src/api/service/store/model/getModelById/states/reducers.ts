/* tslint:disable:max-line-length */
/**
 * OuiMouve API
 * Ensemble des API de OuiMouve
 * M Basier, H Malek, J Brou, S Daudey
 * 1.0
 * undefined
 */

import {createFeatureSelector} from '@ngrx/store';

import {HttpErrorResponse} from '@angular/common/http';
import * as actions from './actions';

export interface GetModelByIdState {
  data: void | null;
  loading: boolean;
  error: HttpErrorResponse | null;
}

export const initialGetModelByIdState: GetModelByIdState = {
  data: null,
  loading: false,
  error: null,
};

export const selectorName = 'Model_GetModelById';
export const getGetModelByIdStateSelector = createFeatureSelector<GetModelByIdState>(selectorName);

export function GetModelByIdReducer(
  state: GetModelByIdState = initialGetModelByIdState,
  action: actions.GetModelByIdAction): GetModelByIdState {
  switch (action.type) {
    case actions.Actions.START: return {...state, loading: true, error: null};
    case actions.Actions.SUCCESS: return {...state, data: action.payload, loading: false};
    case actions.Actions.ERROR: return {...state, error: action.payload, loading: false};
    default: return state;
  }
}
