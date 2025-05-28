import { Component, inject, output } from '@angular/core';
import { InputIconComponent } from '../../../../shared/components/input-icon/input-icon.component';
import { CarPoolingControllerService } from '../../../../service/api/carPoolingController.service';
import { ButtonComponent } from '../../../../shared/components/button/button.component';
import { CarPoolingResponseDto } from '../../../../service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-covoit',
  imports: [InputIconComponent, ButtonComponent],
  templateUrl: './search-covoit.component.html',
  styleUrl: './search-covoit.component.scss',
})
export class SearchCovoitComponent {
  carPoolingService = inject(CarPoolingControllerService);
  searchParameters = {
    siteDeparture: '',
    siteDestination: '',
    startDate: '',
    passanger: 1,
  };
  convoitResults: CarPoolingResponseDto[] | undefined = undefined;
  router = inject(Router);
  searchCovoit() {
    this.carPoolingService
      .filterByCriteria(
        undefined,
        undefined,
        this.searchParameters.startDate || undefined,
        this.searchParameters.siteDeparture
          ? { name: this.searchParameters.siteDeparture }
          : undefined,
        this.searchParameters.siteDestination
          ? { name: this.searchParameters.siteDestination }
          : undefined,
        undefined,
        'body',
        false,
        { httpHeaderAccept: 'application/json' as any }
      )
      .subscribe({
        next: (response) => {
          this.convoitResults = response;
          this.router.navigate(['/search/covoit'], {
            state: { data: this.convoitResults },
          });
        },
        error: (error) => {
          console.error('Error searching for carpooling:', error);
          console.log('Search parameters:', this.searchParameters);
        },
      });
  }
}
