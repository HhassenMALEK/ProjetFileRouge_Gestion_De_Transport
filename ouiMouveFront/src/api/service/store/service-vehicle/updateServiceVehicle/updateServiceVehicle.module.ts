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

import {ServiceVehicleService} from '../../../controllers/ServiceVehicle';
import {FormsSharedModule} from '../../forms-shared.module';
import {UpdateServiceVehicleFormService} from './updateServiceVehicle.service';

import {UpdateServiceVehicleEffects} from './states/effects';
import {UpdateServiceVehicleReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, UpdateServiceVehicleReducer),
    NgrxEffectsModule.forFeature([UpdateServiceVehicleEffects]),
  ],
  providers: [
    ServiceVehicleService,
    UpdateServiceVehicleFormService,
  ],
})
export class UpdateServiceVehicleModule {}
