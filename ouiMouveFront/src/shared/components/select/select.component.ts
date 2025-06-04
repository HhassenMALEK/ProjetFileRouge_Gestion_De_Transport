
import { Component, input, model, signal } from '@angular/core';
import { FormsModule, ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-select',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './select.component.html',
  styleUrl: './select.component.scss',

  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: SelectComponent,
      multi: true
    }
  ]
})
export class SelectComponent implements ControlValueAccessor {
  items = input.required<any[]>();

  displayAttr = input<string>('');
  valueAttr = input<string>('');
  label = input<string>('');
  placeholder = input<string>('Sélectionnez...');
  required = input<boolean>(false);
  name = input<string>('');
  disabled = input<boolean>(false);

  value = model<any>(null);
  isDisabled = signal(false);
  
  private onChange: (value: any) => void = () => {};
  private onTouched: () => void = () => {};

  writeValue(value: any): void {
    this.value.set(value);
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.isDisabled.set(isDisabled);
  }

  onValueChange(event: any): void {
    this.value.set(event);
    this.onChange(event);
    this.onTouched();
  }

}