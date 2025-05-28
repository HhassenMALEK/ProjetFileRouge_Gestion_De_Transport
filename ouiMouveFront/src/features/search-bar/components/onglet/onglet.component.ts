import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-onglet',
  imports: [],
  templateUrl: './onglet.component.html',
  styleUrl: './onglet.component.scss',
})
export class OngletComponent {
  isActive = input<boolean>(false);
  ongletCLicked = output<void>();
  label = input<string>('');

  handleClick() {
    this.ongletCLicked.emit();
  }
}
