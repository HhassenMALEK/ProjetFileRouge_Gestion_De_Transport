import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  CarPoolingReservationsControllerService,
  CarPoolingReservationsResponseDTO,
} from '@openapi/index';
import { CarpoolingCardComponent } from '@shared/components/carpooling-card/carpooling-card.component';

@Component({
  selector: 'app-carpooling-resa-list',
  imports: [CarpoolingCardComponent],
  templateUrl: './carpooling-resa-list.component.html',
  styleUrl: './carpooling-resa-list.component.scss',
})
export class CarpoolingResaListComponent implements OnInit {
  reservations: CarPoolingReservationsResponseDTO[] = [];
  reservationService = inject(CarPoolingReservationsControllerService);
  private router = inject(Router);
  ngOnInit(): void {
    this.reservationService.getAllCarPoolingReservations().subscribe({
      next: (reservations: CarPoolingReservationsResponseDTO[]) => {
        console.log('Received reservations:', reservations);
        this.reservations = reservations;
      },
      error: (error) => {
        console.error('Error fetching reservations:', error);
      },
    });
  }
  handleDetailsClick(id: number): void {
    this.router.navigate([`carpooling-reservation/details/${id}`]);
  }
}
