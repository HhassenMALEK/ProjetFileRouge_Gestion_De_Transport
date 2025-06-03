import { Component, inject, OnInit } from '@angular/core';
import {
  CarPoolingReservationsControllerService,
  CarPoolingReservationsResponseDTO,
} from '../../../../service';
import { CarpoolingResaItemComponent } from '../carpooling-resa-item/carpooling-resa-item.component';
import { extractDateTime } from '../../../../shared/utils/dateUtils';

@Component({
  selector: 'app-carpooling-resa-list',
  imports: [CarpoolingResaItemComponent],
  templateUrl: './carpooling-resa-list.component.html',
  styleUrl: './carpooling-resa-list.component.scss',
})
export class CarpoolingResaListComponent implements OnInit {
  reservations: CarPoolingReservationsResponseDTO[] = [];
  reservationService = inject(CarPoolingReservationsControllerService);
  ngOnInit(): void {
    this.reservationService
      .getAllCarPoolingReservations('body', false, {
        httpHeaderAccept: 'application/json' as any,
      })
      .subscribe({
        next: (reservations: CarPoolingReservationsResponseDTO[]) => {
          console.log('Received reservations:', reservations);
          this.reservations = reservations;
        },
        error: (error) => {
          console.error('Error fetching reservations:', error);
        },
      });
  }
}
