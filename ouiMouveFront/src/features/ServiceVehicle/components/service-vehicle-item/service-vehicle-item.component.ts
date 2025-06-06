import { Component, input } from '@angular/core';
import { ServiceVehicleDto } from '@openapi/index';
import { StatusComponent } from '@shared/components/status/status.component';
import { MatIconModule } from '@angular/material/icon';
import { ButtonComponent } from '@shared/components/button/button.component';

@Component({
  selector: 'app-service-vehicle-item',
 imports: [StatusComponent, MatIconModule, ButtonComponent],
  templateUrl: './service-vehicle-item.component.html',
  styleUrl: './service-vehicle-item.component.scss'
})
export class ServiceVehicleItemComponent {
  serviceVehicle = input <ServiceVehicleDto>();
  color = input<string>('');

  addRepair() {}
}
