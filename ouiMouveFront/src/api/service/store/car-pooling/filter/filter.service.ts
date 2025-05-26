/* tslint:disable:max-line-length */
/**
 * OuiMouve API
 * Ensemble des API de OuiMouve
 * M Basier, H Malek, J Brou, S Daudey
 * 1.0
 * undefined
 */

import {Injectable} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {CarPoolingService} from '../../../controllers/CarPooling';

@Injectable()
export class FilterFormService {
  form: FormGroup;
  constructor(
    private carPoolingService: CarPoolingService,
  ) {
    this.form = new FormGroup({
      organizerId: new FormControl(undefined, []),
      status: new FormControl(undefined, []),
      startDate: new FormControl(undefined, []),
      endDate: new FormControl(undefined, []),
      vehicleId: new FormControl(undefined, []),
    });
  }

  submit(raw = false) {
    const data = raw ?
      this.form.getRawValue() :
      this.form.value;
    return this.carPoolingService.filter(data);
  }
}
