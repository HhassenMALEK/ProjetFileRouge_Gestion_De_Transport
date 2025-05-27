import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { apiStatus } from '../../lib/status';

@Component({
  selector: 'app-status',
  imports: [MatIconModule],
  templateUrl: './status.component.html',
  styleUrl: './status.component.scss'
})
export class StatusComponent {
  status = input<string>();

  getColor() {
    if (!this.status()) return '#000000'; // Default to black if no status is provided
    return apiStatus[this.status()!]?.color;// Default to black if status not found
  }

  getLabel() {
    if (!this.status()) return 'Unknown Status'; // Default label if no status is provided
    return apiStatus[this.status()!]?.label; // Default label if status not found
  }
}
