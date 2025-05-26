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
import {ListParams} from '../../../../controllers/Reparation';

export enum Actions {
  START = '[Reparation list] Start',
  SUCCESS = '[Reparation list] Success',
  ERROR = '[Reparation list] Error',
}

export class Start implements Action {
  readonly type = Actions.START;
  constructor(public payload: ListParams) {}
}

export class Success implements Action {
  readonly type = Actions.SUCCESS;
  constructor(public payload: void) {}
}

export class Error implements Action {
  readonly type = Actions.ERROR;
  constructor(public payload: HttpErrorResponse) {}
}

export type ListAction = Start | Success | Error;
