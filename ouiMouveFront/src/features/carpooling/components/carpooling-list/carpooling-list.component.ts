import { Component, inject, OnInit } from '@angular/core';
import { CarPoolingResponseDto } from '@openapi/model/carPoolingResponseDto';
import { CarPoolingFilteringService } from '@shared/service/carpooling-filtering.service';
import { CarpoolingCardComponent } from '@shared/components/carpooling-card/carpooling-card.component';
@Component({
  selector: 'app-carpooling-list',
  imports: [CarpoolingCardComponent],
  templateUrl: './carpooling-list.component.html',
  styleUrl: './carpooling-list.component.scss',
})
export class CarpoolingListComponent implements OnInit {
  carPoolings: CarPoolingResponseDto[] = [];
  carPoolingFilteringService = inject(CarPoolingFilteringService);
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
  handleReservation(): void {
    // Handle reservation logic here
    console.log('Reservation button clicked');
  }
}
