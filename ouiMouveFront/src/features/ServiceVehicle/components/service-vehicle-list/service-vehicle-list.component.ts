import { Component, inject, OnInit } from '@angular/core';
import { ServiceVehicleDto } from '@openapi/model/serviceVehicleDto';
import { ServiceVehicleFilteringService } from '@shared/service/serviceVehicle-filtering.service';
import { ServiceVehicleItemComponent } from '../service-vehicle-item/service-vehicle-item.component';

@Component({
  selector: 'app-service-vehicle-list',
  imports: [ServiceVehicleItemComponent],
  templateUrl: './service-vehicle-list.component.html',
  styleUrl: './service-vehicle-list.component.scss'
})
export class ServiceVehicleListComponent {

  serviceVehicles: ServiceVehicleDto[] = [];
  color : string = '';
  serviceVehicleFilteringService = inject(ServiceVehicleFilteringService);

  ngOnInit(): void {
    // Fetch service vehicles when the component initializes

    // Subscribe to service vehicle updates from the filtering service
    this.serviceVehicleFilteringService.currentServiceVehicles.subscribe(
      (serviceVehicles: ServiceVehicleDto[]) => {
        console.log('Received service vehicle updates:', serviceVehicles);
        this.serviceVehicles = serviceVehicles;
      }
    );
  }

}
