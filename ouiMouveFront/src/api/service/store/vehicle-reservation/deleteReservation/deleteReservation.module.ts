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
import {DeleteReservationFormService} from './deleteReservation.service';

import {DeleteReservationEffects} from './states/effects';
import {DeleteReservationReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, DeleteReservationReducer),
    NgrxEffectsModule.forFeature([DeleteReservationEffects]),
  ],
  providers: [
    VehicleReservationService,
    DeleteReservationFormService,
  ],
})
export class DeleteReservationModule {}
