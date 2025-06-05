import { Component, input } from '@angular/core';
import { StatusComponent } from '@shared/components/status/status.component';
import { CarPoolingResponseDto } from '@openapi/index';
import { MatIconModule } from '@angular/material/icon';
import { extractDateTime } from '@shared/utils/dateUtils';
import { ButtonComponent } from '@shared/components/button/button.component';

@Component({
  selector: 'app-carpooling-card',
  imports: [StatusComponent, MatIconModule, ButtonComponent],
  templateUrl: './carpooling-card.component.html',
  styleUrl: './carpooling-card.component.scss',
})
export class CarpoolingCardComponent {
  carpooling = input<CarPoolingResponseDto>();
  color = input<string>();
  handleReservation() {
    // TODO détail de la résa
    console.log('Reservation button clicked');
  }
  getFormattedDateTime(dateString?: string) {
    return extractDateTime(dateString);
  }
}
