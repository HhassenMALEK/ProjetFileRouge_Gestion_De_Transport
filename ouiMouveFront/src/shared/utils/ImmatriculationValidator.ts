import { Directive, forwardRef } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, Validator } from '@angular/forms';


@Directive({
  selector: '[immatriculationFormat]',
  standalone: true,
  providers: [{
    provide: NG_VALIDATORS,
    useExisting: forwardRef(() => ImmatriculationValidator),
    multi: true
  }]
})
export class ImmatriculationValidator implements Validator {
  validate(control: AbstractControl): {[key: string]: any} | null {
    const value = control.value;
    if (!value) return null;
    
     const pattern = /^[A-Z]{2}[0-9]{3}[A-Z]{2}$/;
    return pattern.test(value.toUpperCase()) 
      ? null 
      : { immatriculation: true };
  }
}