import { FormsModule } from '@angular/forms';
import { Component, input, model } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-input-icon',
  imports: [MatIconModule, FormsModule],
  templateUrl: './input-icon.component.html',
  styleUrl: './input-icon.component.scss',
})
export class InputIconComponent {
  type = input<string>();
  placeholder = input<string>();
  icon = input<string>();
  value = model<string | number>();
}
