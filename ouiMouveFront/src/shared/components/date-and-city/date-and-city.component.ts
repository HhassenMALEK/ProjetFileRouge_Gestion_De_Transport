import { Component, computed, effect, input } from '@angular/core';
import {
  CarPoolingReservationsResponseDTO,
  CarPoolingResponseDto,
} from '@openapi/index';
import { DateTime } from '@shared/lib/dateTime';
import { extractDateTime, getArrivalDate } from '@shared/utils/dateUtils';

@Component({
  selector: 'app-date-and-city',
  imports: [],
  templateUrl: './date-and-city.component.html',
  styleUrl: './date-and-city.component.scss',
})
export class DateAndCityComponent {
  type = input<string | undefined>(undefined);
  color = input<string>();
  carpooling = input<
    CarPoolingReservationsResponseDTO | CarPoolingResponseDto | undefined
  >(undefined);
  formatedDate: DateTime | null = null;
  extractedCarpooling = computed(() => {
    const cp = this.carpooling();
    if (!cp) return null;

    if (this.isReservationResponse(cp)) {
      return cp.carPooling || null;
    }
    return cp;
  });
  constructor() {
    effect(() => {
      const cp = this.extractedCarpooling();
      if (cp) {
        this.initializeDate(cp);
      }
    });
  }
  private initializeDate(cp: CarPoolingResponseDto): void {
    if (this.type() === 'arrival')
      this.formatedDate = extractDateTime(getArrivalDate(cp)?.toString());
    else this.formatedDate = extractDateTime(cp.departure);
  }

  private isReservationResponse(
    cp: CarPoolingReservationsResponseDTO | CarPoolingResponseDto | undefined
  ): cp is CarPoolingReservationsResponseDTO {
    if (!cp) return false;
    return 'carPooling' in cp;
  }
}
