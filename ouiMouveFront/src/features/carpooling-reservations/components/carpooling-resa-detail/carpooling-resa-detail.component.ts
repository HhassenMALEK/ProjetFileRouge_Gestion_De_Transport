import { Component, inject, OnInit } from '@angular/core';
import { CarPoolingReservationsControllerService } from '@openapi/api/carPoolingReservationsController.service';
import { CarPoolingReservationsResponseDTO } from '@openapi/model/carPoolingReservationsResponseDTO';

import { ActivatedRoute, Router } from '@angular/router';
import { ButtonComponent } from '@shared/components/button/button.component';
import { StatusComponent } from '@shared/components/status/status.component';
import { formatMinIntoHoursAndMinutes } from '@shared/utils/dateUtils';
import { DateAndCityComponent } from '../date-and-city/date-and-city.component';
import { UserListComponent } from '../user-list/user-list.component';
import { VehicleReservationComponent } from '../vehicle-reservation/vehicle-reservation.component';

@Component({
  selector: 'app-carpooling-resa-detail',
  imports: [
    StatusComponent,
    VehicleReservationComponent,
    UserListComponent,
    DateAndCityComponent,
    ButtonComponent,
  ],
  templateUrl: './carpooling-resa-detail.component.html',
  styleUrl: './carpooling-resa-detail.component.scss',
})
export class CarpoolingResaDetailComponent implements OnInit {
  reservation?: CarPoolingReservationsResponseDTO;
  private resaService = inject(CarPoolingReservationsControllerService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  ngOnInit(): void {
    this.loadReservation();
  }

  formatHoursAndMinutes(minutes: number | undefined): string {
    if (!minutes) return '0h00min';
    return formatMinIntoHoursAndMinutes(minutes);
  }
  private loadReservation(): void {
    const reservationId = this.route.snapshot.paramMap.get('id');
    if (reservationId) {
      this.resaService
        .getCarPoolingReservation(parseInt(reservationId))
        .subscribe({
          next: (reservation: CarPoolingReservationsResponseDTO) => {
            this.reservation = reservation;
          },
          error: (error) => {
            this.router.navigate(['/carpooling-reservation']);
          },
        });
    }
  }
  handleAnnulation(): void {
    console.log('Annulation clicked');
  }
}
