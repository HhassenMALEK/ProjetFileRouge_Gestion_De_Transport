import { Component, inject, OnInit } from '@angular/core';
import {
  CarPoolingReservationsControllerService,
  CarPoolingReservationsResponseDTO,
} from '../../../../service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-carpooling-resa-detail',
  imports: [],
  templateUrl: './carpooling-resa-detail.component.html',
  styleUrl: './carpooling-resa-detail.component.scss',
})
export class CarpoolingResaDetailComponent implements OnInit {
  reservation?: CarPoolingReservationsResponseDTO;
  resaService = inject(CarPoolingReservationsControllerService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  ngOnInit(): void {
    const reservationId = this.route.snapshot.paramMap.get('id');
    if (reservationId)
      this.resaService
        .getCarPoolingReservation(parseInt(reservationId), 'body', false, {
          httpHeaderAccept: 'application/json' as any,
        })
        .subscribe({
          next: (reservation: CarPoolingReservationsResponseDTO) => {
            console.log('Received reservation:', reservation);
            this.reservation = reservation;
          },
          error: (error) => {
            console.error('Error fetching reservation:', error);
            // Optionally, navigate back to the list or show an error message
            this.router.navigate(['/carpooling-reservation']);
          },
        });
  }
}
