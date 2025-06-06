import { Component, inject, input, effect } from '@angular/core';
import {
  PersonalVehicleControllerService,
  ServiceVehicleControllerService,
} from '@openapi/api/api';
import {
  PersonalVehicleDto,
  ServiceVehicleDto,
  VehicleDto,
} from '@openapi/index';

@Component({
  selector: 'app-vehicle-reservation',
  imports: [],
  templateUrl: './vehicle-reservation.component.html',
  styleUrl: './vehicle-reservation.component.scss',
})
export class VehicleReservationComponent {
  private serviceVehicleService = inject(ServiceVehicleControllerService);
  private personalVehicleService = inject(PersonalVehicleControllerService);
  vehicle = input<VehicleDto | undefined>(undefined);
  serviceVehicle: ServiceVehicleDto | undefined = undefined;
  personalVehicle: PersonalVehicleDto | undefined = undefined;

  constructor() {
    effect(() => {
      const vehicleData = this.vehicle();
      if (vehicleData?.id) {
        this.loadVehicleData(vehicleData.id);
      }
    });
  }

  private loadVehicleData(vehicleId: number): void {
    this.personalVehicleService.getPersonalVehicle(vehicleId).subscribe({
      next: (personalVehicle: PersonalVehicleDto) => {
        this.personalVehicle = personalVehicle;
        console.log('Personal Vehicle:', this.personalVehicle);
      },
      error: (error) => {
        console.error('Error fetching personal vehicle:', error);
      },
    });

    this.serviceVehicleService.getServiceVehicle(vehicleId).subscribe({
      next: (serviceVehicle: ServiceVehicleDto) => {
        this.serviceVehicle = serviceVehicle;
        console.log('Service Vehicle:', this.serviceVehicle);
      },
      error: (error) => {
        console.error('Error fetching service vehicle:', error);
      },
    });
  }
}
