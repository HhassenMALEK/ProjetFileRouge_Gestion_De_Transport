import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ServiceVehicleDto } from '@openapi/index';

@Injectable({
  providedIn: 'root',
})
export class ServiceVehicleFilteringService {
  private serviceVehicles = new BehaviorSubject<ServiceVehicleDto[]>([]);
  currentServiceVehicles = this.serviceVehicles.asObservable();

  sendServiceVehicle(newServiceVehicles: ServiceVehicleDto[]) {
    this.serviceVehicles.next(newServiceVehicles);
  }
}
