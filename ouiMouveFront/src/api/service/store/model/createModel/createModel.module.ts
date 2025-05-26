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

import {ModelService} from '../../../controllers/Model';
import {FormsSharedModule} from '../../forms-shared.module';

import {CreateModelEffects} from './states/effects';
import {CreateModelReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, CreateModelReducer),
    NgrxEffectsModule.forFeature([CreateModelEffects]),
  ],
  providers: [
    ModelService,
  ],
})
export class CreateModelModule {}
