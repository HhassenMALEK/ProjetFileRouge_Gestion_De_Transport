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
import {UpdateCarPoolingFormService} from './updateCarPooling.service';

import {UpdateCarPoolingEffects} from './states/effects';
import {UpdateCarPoolingReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, UpdateCarPoolingReducer),
    NgrxEffectsModule.forFeature([UpdateCarPoolingEffects]),
  ],
  providers: [
    CarPoolingService,
    UpdateCarPoolingFormService,
  ],
})
export class UpdateCarPoolingModule {}
