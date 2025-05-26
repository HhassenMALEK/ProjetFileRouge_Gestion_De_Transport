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

import {CreateCarPoolingEffects} from './states/effects';
import {CreateCarPoolingReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, CreateCarPoolingReducer),
    NgrxEffectsModule.forFeature([CreateCarPoolingEffects]),
  ],
  providers: [
    CarPoolingService,
  ],
})
export class CreateCarPoolingModule {}
