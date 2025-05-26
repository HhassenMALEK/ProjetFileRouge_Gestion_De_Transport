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
import {CarPoolingReservationsService} from '../../../controllers/CarPoolingReservations';

@Injectable()
export class CancelFormService {
  form: FormGroup;
  constructor(
    private carPoolingReservationsService: CarPoolingReservationsService,
  ) {
    this.form = new FormGroup({
      resId: new FormControl(undefined, [Validators.required]),
    });
  }

  submit(raw = false) {
    const data = raw ?
      this.form.getRawValue() :
      this.form.value;
    return this.carPoolingReservationsService.cancel(data);
  }
}
