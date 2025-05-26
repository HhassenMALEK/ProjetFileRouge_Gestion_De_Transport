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
import {DeleteReparationFormService} from './deleteReparation.service';

import {DeleteReparationEffects} from './states/effects';
import {DeleteReparationReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, DeleteReparationReducer),
    NgrxEffectsModule.forFeature([DeleteReparationEffects]),
  ],
  providers: [
    ReparationService,
    DeleteReparationFormService,
  ],
})
export class DeleteReparationModule {}
