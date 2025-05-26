/* tslint:disable:max-line-length */
/**
 * OuiMouve API
 * Ensemble des API de OuiMouve
 * M Basier, H Malek, J Brou, S Daudey
 * 1.0
 * undefined
 */

import {Injectable} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AdressService} from '../../../controllers/Adress';

@Injectable()
export class GetAdressByLabelAndCityFormService {
  form: FormGroup;
  constructor(
    private adressService: AdressService,
  ) {
    this.form = new FormGroup({
      label: new FormControl(undefined, [Validators.required]),
      city: new FormControl(undefined, [Validators.required]),
    });
  }

  submit(raw = false) {
    const data = raw ?
      this.form.getRawValue() :
      this.form.value;
    return this.adressService.getAdressByLabelAndCity(data);
  }
}
