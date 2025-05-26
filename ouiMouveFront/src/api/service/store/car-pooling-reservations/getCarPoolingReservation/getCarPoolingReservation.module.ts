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

import {CarPoolingReservationsService} from '../../../controllers/CarPoolingReservations';
import {FormsSharedModule} from '../../forms-shared.module';
import {GetCarPoolingReservationFormService} from './getCarPoolingReservation.service';

import {GetCarPoolingReservationEffects} from './states/effects';
import {GetCarPoolingReservationReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, GetCarPoolingReservationReducer),
    NgrxEffectsModule.forFeature([GetCarPoolingReservationEffects]),
  ],
  providers: [
    CarPoolingReservationsService,
    GetCarPoolingReservationFormService,
  ],
})
export class GetCarPoolingReservationModule {}
