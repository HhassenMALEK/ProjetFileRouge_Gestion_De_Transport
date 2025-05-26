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
export class GetAdressByLatAndLongFormService {
  form: FormGroup;
  constructor(
    private adressService: AdressService,
  ) {
    this.form = new FormGroup({
      latX: new FormControl(undefined, [Validators.required]),
      longY: new FormControl(undefined, [Validators.required]),
    });
  }

  submit(raw = false) {
    const data = raw ?
      this.form.getRawValue() :
      this.form.value;
    return this.adressService.getAdressByLatAndLong(data);
  }
}
