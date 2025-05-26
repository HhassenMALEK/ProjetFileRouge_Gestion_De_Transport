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

import {UserService} from '../../../controllers/User';
import {FormsSharedModule} from '../../forms-shared.module';
import {GetUserByIdFormService} from './getUserById.service';

import {GetUserByIdEffects} from './states/effects';
import {GetUserByIdReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, GetUserByIdReducer),
    NgrxEffectsModule.forFeature([GetUserByIdEffects]),
  ],
  providers: [
    UserService,
    GetUserByIdFormService,
  ],
})
export class GetUserByIdModule {}
