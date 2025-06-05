import { Component, effect, input, OnInit } from '@angular/core';
import { CarPoolingReservationsResponseDTO } from '@openapi/index';
import { DateTime } from '@shared/lib/dateTime';
import {
  extractDateTime,
  formatMinIntoHoursAndMinutes,
  getArrivalDate,
} from '@shared/utils/dateUtils';

@Component({
  selector: 'app-date-and-city',
  imports: [],
  templateUrl: './date-and-city.component.html',
  styleUrl: './date-and-city.component.scss',
})
export class DateAndCityComponent {
  type = input<string | undefined>(undefined);
  reservation = input<CarPoolingReservationsResponseDTO | undefined>(undefined);
  formatedDate: DateTime | null = null;
  constructor() {
    effect(() => {
      const res = this.reservation();
      if (res) {
        this.initializeDate(res);
      }
    });
  }
  private initializeDate(
    res: CarPoolingReservationsResponseDTO | undefined
  ): void {
    if (!res) return;
    if (this.type() === 'arrival')
      this.formatedDate = extractDateTime(getArrivalDate(res)?.toString());
    else this.formatedDate = extractDateTime(res.carPooling?.departure);
  }
}
