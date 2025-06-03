import { Component, input, output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-select',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './select.component.html',
  styleUrl: './select.component.scss',
})
export class SelectComponent {
  items = input<any[]>([]);
  displayAttr = input<string>('');
  valueAttr = input<string>('');
  label = input<string>('');
  placeholder = input<string>('Sélectionnez...');
  required = input<boolean>(false);
  name = input<string>('');
  disabled = input<boolean>(false);
  value = input<any>(null);

  valueChange = output<any>();
}