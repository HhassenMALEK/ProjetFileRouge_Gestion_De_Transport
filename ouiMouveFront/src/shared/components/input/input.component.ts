import { Component, input, model } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-input',
  imports: [FormsModule],
  templateUrl: './input.component.html',
  styleUrl: './input.component.scss',
})
export class InputComponent {
  label = input<string>('');
  placeholder = input<string>('');
  value = model<string>('');
  type = input<string>('text');
  required = input<boolean>(false);
  disabled = input<boolean>(false);
  name = input<string>('');
}
