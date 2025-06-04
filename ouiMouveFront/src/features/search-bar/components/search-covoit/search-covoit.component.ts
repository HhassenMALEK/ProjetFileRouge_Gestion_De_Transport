import { Component, inject, OnInit } from '@angular/core';
import { InputIconComponent } from '@shared/components/input-icon/input-icon.component';
import { CarPoolingControllerService } from '@openapi/api/carPoolingController.service';
import { ButtonComponent } from '@shared/components/button/button.component';
import { CarPoolingResponseDto } from '@openapi/index';
import { Router } from '@angular/router';
import { CarPoolingFilteringService } from '@shared/service/carpooling-filtering.service';

@Component({
  selector: 'app-search-covoit',
  imports: [InputIconComponent, ButtonComponent],
  templateUrl: './search-covoit.component.html',
  styleUrl: './search-covoit.component.scss',
})
export class SearchCovoitComponent implements OnInit {
  carPoolingService = inject(CarPoolingControllerService);
  carPoolingFilteringService = inject(CarPoolingFilteringService);
  searchParameters = {
    siteDeparture: '',
    siteDestination: '',
    startDate: '',
    endDate: '',
    capacity: 1,
  };
  convoitResults: CarPoolingResponseDto[] | undefined = undefined;
  router = inject(Router);
  ngOnInit(): void {
    // Initialize search parameters or fetch initial data if needed
    this.searchCovoit();
  }
  searchCovoit() {
    this.carPoolingService
      .filterByCriteria(
        undefined,
        undefined,
        this.searchParameters.startDate || undefined,
        this.searchParameters.endDate || undefined,
        this.searchParameters.siteDeparture || undefined,
        this.searchParameters.siteDestination || undefined,
        undefined,
        this.searchParameters.capacity || undefined
      )
      .subscribe({
        next: (response) => {
          this.convoitResults = response;
          console.log(
            'Carpooling search results from search covoit:',
            this.convoitResults
          );
          this.carPoolingFilteringService.sendCarPoolings(
            this.convoitResults || []
          );
        },
        error: (error) => {
          console.error('Error searching for carpooling:', error);
          console.log('Search parameters:', this.searchParameters);
        },
      });
  }
}
