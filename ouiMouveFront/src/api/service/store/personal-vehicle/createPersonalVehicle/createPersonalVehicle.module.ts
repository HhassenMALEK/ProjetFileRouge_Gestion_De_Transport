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

import {PersonalVehicleService} from '../../../controllers/PersonalVehicle';
import {FormsSharedModule} from '../../forms-shared.module';

import {CreatePersonalVehicleEffects} from './states/effects';
import {CreatePersonalVehicleReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, CreatePersonalVehicleReducer),
    NgrxEffectsModule.forFeature([CreatePersonalVehicleEffects]),
  ],
  providers: [
    PersonalVehicleService,
  ],
})
export class CreatePersonalVehicleModule {}
