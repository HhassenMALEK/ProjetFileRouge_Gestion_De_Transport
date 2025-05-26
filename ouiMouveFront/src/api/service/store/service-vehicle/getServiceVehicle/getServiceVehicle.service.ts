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
import {ServiceVehicleService} from '../../../controllers/ServiceVehicle';

@Injectable()
export class GetServiceVehicleFormService {
  form: FormGroup;
  constructor(
    private serviceVehicleService: ServiceVehicleService,
  ) {
    this.form = new FormGroup({
      id: new FormControl(undefined, [Validators.required]),
    });
  }

  submit(raw = false) {
    const data = raw ?
      this.form.getRawValue() :
      this.form.value;
    return this.serviceVehicleService.getServiceVehicle(data);
  }
}
