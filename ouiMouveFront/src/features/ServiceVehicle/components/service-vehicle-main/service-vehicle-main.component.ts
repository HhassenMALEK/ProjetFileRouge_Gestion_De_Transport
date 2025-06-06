import { Component } from '@angular/core';
import { ServiceVehicleListComponent } from '../service-vehicle-list/service-vehicle-list.component';
import { ServiceVehicleSearchComponent } from '../service-vehicle-search/service-vehicle-search.component';

@Component({
  selector: 'app-service-vehicle-main',
  imports: [ServiceVehicleListComponent,ServiceVehicleSearchComponent],
  templateUrl: './service-vehicle-main.component.html',
  styleUrl: './service-vehicle-main.component.scss'
})
export class ServiceVehicleMainComponent {

}
