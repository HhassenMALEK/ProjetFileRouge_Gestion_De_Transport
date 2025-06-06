import { Component, inject, OnInit } from '@angular/core';
import { CarPoolingResponseDto } from '@openapi/model/carPoolingResponseDto';
import { CarPoolingFilteringService } from '@shared/service/carpooling-filtering.service';
import { CarpoolingCardComponent } from '@shared/components/carpooling-card/carpooling-card.component';
import { AuthService } from '@shared/service/auth.service';
import {
  CarPoolingReservationsControllerService,
  CarPoolingReservationsCreateDTO,
} from '@openapi/index';
@Component({
  selector: 'app-carpooling-list',
  imports: [CarpoolingCardComponent],
  templateUrl: './carpooling-list.component.html',
  styleUrl: './carpooling-list.component.scss',
})
export class CarpoolingListComponent implements OnInit {
  carPoolings: CarPoolingResponseDto[] = [];
  carPoolingFilteringService = inject(CarPoolingFilteringService);
  auth = inject(AuthService);
  reservationService = inject(CarPoolingReservationsControllerService);
  ngOnInit(): void {
    // Fetch carpoolings when the component initializes

    // Subscribe to carpooling updates from the filtering service
    this.carPoolingFilteringService.currentCarPoolings.subscribe(
      (carPoolings: CarPoolingResponseDto[]) => {
        console.log('Received carpooling updates:', carPoolings);
        this.carPoolings = carPoolings;
      }
    );
  }
  handleReservation(carpoolingId: number): void {
    const dto: CarPoolingReservationsCreateDTO = {
      carPoolingId: carpoolingId,
    };
    this.reservationService.createCarPoolingReservation(dto).subscribe({
      next: (response) => {
        console.log('Reservation created successfully:', response);
        // Optionally, you can refresh the carpooling list or show a success message
      },
      error: (error) => {
        console.error('Error creating reservation:', error);
        // Handle error appropriately, e.g., show an error message to the user
      },
    });
  }
}
