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

import {AdressService} from '../../../controllers/Adress';
import {FormsSharedModule} from '../../forms-shared.module';
import {DeleteAdressFormService} from './deleteAdress.service';

import {DeleteAdressEffects} from './states/effects';
import {DeleteAdressReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, DeleteAdressReducer),
    NgrxEffectsModule.forFeature([DeleteAdressEffects]),
  ],
  providers: [
    AdressService,
    DeleteAdressFormService,
  ],
})
export class DeleteAdressModule {}
