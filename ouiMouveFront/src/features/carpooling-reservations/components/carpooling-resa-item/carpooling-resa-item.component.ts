import { Component, inject, input, OnInit } from '@angular/core';
import { CarPoolingReservationsResponseDTO } from '../../../../service';
import { extractDateTime } from '../../../../shared/utils/dateUtils';
import { Router } from '@angular/router';
import { ButtonComponent } from '../../../../shared/components/button/button.component';
import { StatusComponent } from '../../../../shared/components/status/status.component';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-carpooling-resa-item',
  imports: [ButtonComponent, StatusComponent, MatIconModule],
  templateUrl: './carpooling-resa-item.component.html',
  styleUrl: './carpooling-resa-item.component.scss',
})
export class CarpoolingResaItemComponent implements OnInit {
  reservation = input<CarPoolingReservationsResponseDTO>();
  arrivalDate: any;
  router = inject(Router);
  ngOnInit(): void {
    this.arrivalDate = this.getFormattedDateTime(
      this.getArrivalDateTime()?.toString()
    );
  }
  handleDetailsClick() {
    this.router.navigate([
      `carpooling-reservation/details/${this.reservation()?.id}`,
    ]);
  }
  getFormattedDateTime(dateString?: string) {
    return extractDateTime(dateString);
  }
  getArrivalDateTime(): Date | undefined {
    const departureValue = this.reservation()?.carPooling?.departure;
    const departureDate =
      departureValue !== undefined ? new Date(departureValue) : undefined;
    const durationInMinutes = this.reservation()?.carPooling?.durationInMinutes;
    const arrivalTimeInSeconds =
      departureDate && durationInMinutes !== undefined
        ? departureDate.getTime() / 1000 + durationInMinutes * 60
        : undefined;
    if (arrivalTimeInSeconds) {
      return new Date(arrivalTimeInSeconds * 1000);
    }
    return undefined;
  }
}
