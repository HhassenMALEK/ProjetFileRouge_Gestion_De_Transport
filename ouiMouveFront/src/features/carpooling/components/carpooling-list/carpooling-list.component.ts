import { Component, inject } from '@angular/core';
import { SearchBarComponent } from '../search-bar/search-bar.component';
import { CarpoolingCardComponent } from '../carpooling-card/carpooling-card.component';
import { CarPoolingControllerService } from '../../../../api/api/carPoolingController.service';
import { CarPoolingResponseDto } from '../../../../api';

@Component({
  selector: 'app-carpooling-list',
  imports: [CarpoolingCardComponent, SearchBarComponent],
  templateUrl: './carpooling-list.component.html',
  styleUrl: './carpooling-list.component.scss'
})
export class CarpoolingListComponent {
  carPoolingService = inject(CarPoolingControllerService);
  carPoolings: CarPoolingResponseDto[] = [];
  ngOnInit() {
    // Call getAll with 'body' for observe, false for reportProgress, 
    // and options specifying httpHeaderAccept as 'application/json'
    this.carPoolingService.getAll(
      'body', // observe
      false,  // reportProgress
      { httpHeaderAccept: 'application/json'  as any} // options
    ).subscribe({
      next: (response: CarPoolingResponseDto[]) => { // Type the response
        this.carPoolings = response;
      },
      error: (error) => {
        console.error('Error fetching carpoolings:', error);
      }
    });
  }

}
