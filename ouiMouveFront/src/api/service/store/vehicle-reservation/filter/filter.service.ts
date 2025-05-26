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
import {VehicleReservationService} from '../../../controllers/VehicleReservation';

@Injectable()
export class FilterFormService {
  form: FormGroup;
  constructor(
    private vehicleReservationService: VehicleReservationService,
  ) {
    this.form = new FormGroup({
      userId: new FormControl(undefined, [Validators.required]),
      start: new FormControl(undefined, []),
      status: new FormControl(undefined, []),
    });
  }

  submit(raw = false) {
    const data = raw ?
      this.form.getRawValue() :
      this.form.value;
    return this.vehicleReservationService.filter(data);
  }
}
