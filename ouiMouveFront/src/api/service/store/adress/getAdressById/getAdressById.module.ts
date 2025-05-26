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
import {GetAdressByIdFormService} from './getAdressById.service';

import {GetAdressByIdEffects} from './states/effects';
import {GetAdressByIdReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, GetAdressByIdReducer),
    NgrxEffectsModule.forFeature([GetAdressByIdEffects]),
  ],
  providers: [
    AdressService,
    GetAdressByIdFormService,
  ],
})
export class GetAdressByIdModule {}
