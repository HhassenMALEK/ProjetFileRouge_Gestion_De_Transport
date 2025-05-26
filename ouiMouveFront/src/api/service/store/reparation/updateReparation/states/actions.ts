/* tslint:disable:max-line-length max-classes-per-file */
/**
 * OuiMouve API
 * Ensemble des API de OuiMouve
 * M Basier, H Malek, J Brou, S Daudey
 * 1.0
 * undefined
 */

import {HttpErrorResponse} from '@angular/common/http';
import {Action} from '@ngrx/store';
import {UpdateReparationParams} from '../../../../controllers/Reparation';

export enum Actions {
  START = '[Reparation updateReparation] Start',
  SUCCESS = '[Reparation updateReparation] Success',
  ERROR = '[Reparation updateReparation] Error',
}

export class Start implements Action {
  readonly type = Actions.START;
  constructor(public payload: UpdateReparationParams) {}
}

export class Success implements Action {
  readonly type = Actions.SUCCESS;
  constructor(public payload: void) {}
}

export class Error implements Action {
  readonly type = Actions.ERROR;
  constructor(public payload: HttpErrorResponse) {}
}

export type UpdateReparationAction = Start | Success | Error;
