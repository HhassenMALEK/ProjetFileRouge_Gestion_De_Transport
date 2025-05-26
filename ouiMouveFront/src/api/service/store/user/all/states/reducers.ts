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

export interface AllState {
  data: void | null;
  loading: boolean;
  error: HttpErrorResponse | null;
}

export const initialAllState: AllState = {
  data: null,
  loading: false,
  error: null,
};

export const selectorName = 'User_All';
export const getAllStateSelector = createFeatureSelector<AllState>(selectorName);

export function AllReducer(
  state: AllState = initialAllState,
  action: actions.AllAction): AllState {
  switch (action.type) {
    case actions.Actions.START: return {...state, loading: true, error: null};
    case actions.Actions.SUCCESS: return {...state, data: action.payload, loading: false};
    case actions.Actions.ERROR: return {...state, error: action.payload, loading: false};
    default: return state;
  }
}
