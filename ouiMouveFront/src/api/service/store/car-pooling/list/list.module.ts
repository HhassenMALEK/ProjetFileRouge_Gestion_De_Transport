/* tslint:disable:max-line-length */
/**
 * OuiMouve API
 * Ensemble des API de OuiMouve
 * M Basier, H Malek, J Brou, S Daudey
 * 1.0
 * undefined
 */

import {NgModule} from '@angular/core';
import {EffectsModule as NgrxEffectsModule} from '@ngrx/effects';
import {StoreModule as NgrxStoreModule} from '@ngrx/store';

import {CarPoolingService} from '../../../controllers/CarPooling';
import {FormsSharedModule} from '../../forms-shared.module';

import {ListEffects} from './states/effects';
import {ListReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, ListReducer),
    NgrxEffectsModule.forFeature([ListEffects]),
  ],
  providers: [
    CarPoolingService,
  ],
})
export class ListModule {}
