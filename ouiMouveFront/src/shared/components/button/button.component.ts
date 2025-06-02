import { Component, signal, input, output } from '@angular/core';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-button',
  imports: [CommonModule],
  templateUrl: './button.component.html',
  styleUrl: './button.component.scss',
})
export class ButtonComponent {
  label = input<string>('');
  isDisabled = input(false);
  color = input<string>('');
  onClick = output<void>();
}
