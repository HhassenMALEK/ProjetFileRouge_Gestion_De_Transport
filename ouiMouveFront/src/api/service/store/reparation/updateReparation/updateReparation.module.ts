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

import {ReparationService} from '../../../controllers/Reparation';
import {FormsSharedModule} from '../../forms-shared.module';
import {UpdateReparationFormService} from './updateReparation.service';

import {UpdateReparationEffects} from './states/effects';
import {UpdateReparationReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, UpdateReparationReducer),
    NgrxEffectsModule.forFeature([UpdateReparationEffects]),
  ],
  providers: [
    ReparationService,
    UpdateReparationFormService,
  ],
})
export class UpdateReparationModule {}
