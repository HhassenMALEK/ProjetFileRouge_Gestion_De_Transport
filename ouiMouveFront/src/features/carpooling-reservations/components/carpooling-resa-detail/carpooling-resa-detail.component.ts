import { Component, inject, OnInit } from '@angular/core';
import { CarPoolingReservationsControllerService } from '@openapi/api/carPoolingReservationsController.service';
import { ServiceVehicleControllerService } from '@openapi/api/serviceVehicleController.service';  
import { PersonalVehicleControllerService } from '@openapi/api/personalVehicleController.service';
import { CarPoolingReservationsResponseDTO } from '@openapi/model/carPoolingReservationsResponseDTO';







import { ActivatedRoute, Router } from '@angular/router';
import { StatusComponent } from '@shared/components/status/status.component';
import {
  extractDateTime,
  formatMinIntoHoursAndMinutes,
  getArrivalDate,
} from '@shared/utils/dateUtils';
import { DateTime } from '@shared/lib/dateTime';

@Component({
  selector: 'app-carpooling-resa-detail',
  imports: [StatusComponent],
  templateUrl: './carpooling-resa-detail.component.html',
  styleUrl: './carpooling-resa-detail.component.scss',
})
export class CarpoolingResaDetailComponent implements OnInit {
  reservation?: CarPoolingReservationsResponseDTO;
  departureDate: DateTime = { date: '', time: '' };
  arrivalDate: DateTime = { date: '', time: '' };
  private resaService = inject(CarPoolingReservationsControllerService);
  private serviceVehicleService = inject(ServiceVehicleControllerService);
  private personalVehicleService = inject(PersonalVehicleControllerService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  ngOnInit(): void {
    this.loadReservation();

    console.log('Departure Date:', this.departureDate);
  }
  formatHoursAndMinutes(minutes: number | undefined): string {
    if (!minutes) return '0h00min';
    return formatMinIntoHoursAndMinutes(minutes);
  }
  private async loadReservation(): Promise<void> {
    const reservationId = this.route.snapshot.paramMap.get('id');
    if (reservationId) {
      this.resaService
        .getCarPoolingReservation(parseInt(reservationId))
        .subscribe({
          next: (reservation: CarPoolingReservationsResponseDTO) => {
            console.log('Received reservation:', reservation);
            this.reservation = reservation;
            this.departureDate = this.initializeDate(
              reservation.carPooling?.departure
            );
            this.arrivalDate = this.initializeDate(
              getArrivalDate(reservation)?.toString()
            );
          },
          error: (error) => {
            console.error('Error fetching reservation:', error);
            // Optionally, navigate back to the list or show an error message
            this.router.navigate(['/carpooling-reservation']);
          },
        });
    }
  }
  private initializeDate(date: string | undefined): DateTime {
    const extractedDateTime = extractDateTime(date);
    console.log('Extracted DateTime:', extractedDateTime);
    return extractedDateTime || { date: '', time: '' };
  }
}
