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
import {DeleteUserFormService} from './deleteUser.service';

import {DeleteUserEffects} from './states/effects';
import {DeleteUserReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, DeleteUserReducer),
    NgrxEffectsModule.forFeature([DeleteUserEffects]),
  ],
  providers: [
    UserService,
    DeleteUserFormService,
  ],
})
export class DeleteUserModule {}
