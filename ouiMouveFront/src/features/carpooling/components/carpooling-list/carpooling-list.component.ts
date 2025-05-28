import { Component, inject } from '@angular/core';
import { CarpoolingCardComponent } from '../carpooling-card/carpooling-card.component';
import { CarPoolingControllerService } from '../../../../service/api/carPoolingController.service';
import { CarPoolingResponseDto } from '../../../../service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-carpooling-list',
  imports: [CarpoolingCardComponent],
  templateUrl: './carpooling-list.component.html',
  styleUrl: './carpooling-list.component.scss',
})
export class CarpoolingListComponent {
  carPoolingService = inject(CarPoolingControllerService);
  carPoolings: CarPoolingResponseDto[] = [];
  router = inject(Router);
  ngOnInit() {
    // Attempt to get data from router state
    const navigation = this.router.getCurrentNavigation();
    const stateData = navigation?.extras.state?.['data'] as
      | CarPoolingResponseDto[]
      | undefined;

    if (stateData) {
      console.log('Data received from state:', stateData);
      this.carPoolings = stateData;
    } else {
      // Fallback to fetching all carpoolings if no state data is found
      console.log('No state data found, fetching all carpoolings.');
      this.carPoolingService
        .getAll(
          'body', // observe
          false, // reportProgress
          { httpHeaderAccept: 'application/json' as any } // options
        )
        .subscribe({
          next: (response: CarPoolingResponseDto[]) => {
            this.carPoolings = response;
          },
          error: (error) => {
            console.error('Error fetching carpoolings:', error);
          },
        });
    }
  }
}
