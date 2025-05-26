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

export interface DeleteAdressState {
  data: void | null;
  loading: boolean;
  error: HttpErrorResponse | null;
}

export const initialDeleteAdressState: DeleteAdressState = {
  data: null,
  loading: false,
  error: null,
};

export const selectorName = 'Adress_DeleteAdress';
export const getDeleteAdressStateSelector = createFeatureSelector<DeleteAdressState>(selectorName);

export function DeleteAdressReducer(
  state: DeleteAdressState = initialDeleteAdressState,
  action: actions.DeleteAdressAction): DeleteAdressState {
  switch (action.type) {
    case actions.Actions.START: return {...state, loading: true, error: null};
    case actions.Actions.SUCCESS: return {...state, data: action.payload, loading: false};
    case actions.Actions.ERROR: return {...state, error: action.payload, loading: false};
    default: return state;
  }
}
