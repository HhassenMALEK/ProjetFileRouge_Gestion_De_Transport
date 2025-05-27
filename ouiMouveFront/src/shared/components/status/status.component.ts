import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-status',
  imports: [MatIconModule],
  templateUrl: './status.component.html',
  styleUrl: './status.component.scss'
})
export class StatusComponent {
  status = input<string>();

  getColor() {
    switch (this.status()) {
      case 'success':
        return 'green';
      case 'error':
        return '#B53D3D';
      case 'warning':
        return 'orange';
      case 'info':
        return 'blue';
      default:
        return "gray";
    }
  }

  getLabel() {
    switch (this.status()) {
      case 'success':
        return  'Succès';
      case 'error':
        return 'Erreur';
      case 'warning':
        return 'Attention';
      case 'info':
        return 'Information';
      default:
        return 'Statut inconnu';
    }
  }
}
