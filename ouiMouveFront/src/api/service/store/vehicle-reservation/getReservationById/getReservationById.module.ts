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

import {VehicleReservationService} from '../../../controllers/VehicleReservation';
import {FormsSharedModule} from '../../forms-shared.module';
import {GetReservationByIdFormService} from './getReservationById.service';

import {GetReservationByIdEffects} from './states/effects';
import {GetReservationByIdReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, GetReservationByIdReducer),
    NgrxEffectsModule.forFeature([GetReservationByIdEffects]),
  ],
  providers: [
    VehicleReservationService,
    GetReservationByIdFormService,
  ],
})
export class GetReservationByIdModule {}
