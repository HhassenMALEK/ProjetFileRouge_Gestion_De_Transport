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

import {SiteService} from '../../../controllers/Site';
import {FormsSharedModule} from '../../forms-shared.module';
import {GetSiteByIdFormService} from './getSiteById.service';

import {GetSiteByIdEffects} from './states/effects';
import {GetSiteByIdReducer} from './states/reducers';
import {selectorName} from './states/reducers';

@NgModule({
  imports: [
    FormsSharedModule,
    NgrxStoreModule.forFeature(selectorName, GetSiteByIdReducer),
    NgrxEffectsModule.forFeature([GetSiteByIdEffects]),
  ],
  providers: [
    SiteService,
    GetSiteByIdFormService,
  ],
})
export class GetSiteByIdModule {}
