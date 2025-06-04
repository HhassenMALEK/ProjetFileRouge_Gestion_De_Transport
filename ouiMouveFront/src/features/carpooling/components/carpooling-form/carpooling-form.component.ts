import { Component, OnInit, Output, EventEmitter, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { InputIconComponent } from '@shared/components/input-icon/input-icon.component';
import { ButtonComponent } from '@shared/components/button/button.component';
import { CarPoolingControllerService } from '../../../../service/api/carPoolingController.service';
import { ServiceVehicleControllerService } from '../../../../service/api/serviceVehicleController.service';
import { CarPoolingCreateDto } from '../../../../service/model/carPoolingCreateDto';
import { ServiceVehicleDto } from '../../../../service/model/serviceVehicleDto';
import { SelectComponent } from '@shared/components/select/select.component';
import {
  PersonalVehicleControllerService,
  PersonalVehicleDto,
} from '../../../../service';

@Component({
  selector: 'app-carpooling-form',
  imports: [
    CommonModule,
    FormsModule,
    InputIconComponent,
    ButtonComponent,
    SelectComponent,
  ],
  templateUrl: './carpooling-form.component.html',
  styleUrl: './carpooling-form.component.scss',
})
export class CarpoolingFormComponent implements OnInit {
  carPoolingService = inject(CarPoolingControllerService);
  serviceVehicleService = inject(ServiceVehicleControllerService);
  personalVehiclesService = inject(PersonalVehicleControllerService);
  serviceVehicle: ServiceVehicleDto[] = [];
  personalVehicles: PersonalVehicleDto[] = [];

  carpooling: CarPoolingCreateDto = {
    departureSiteId: 0,
    destinationSiteId: 0,
    departure: '',
    vehicleId: 0,
  };

  isSubmitting = false; //États internes / événements
  @Output() cancel = new EventEmitter<void>();
  @Output() created = new EventEmitter<CarPoolingCreateDto>();

  ngOnInit(): void {
    this.loadPersonalVehicles();
  }

  loadPersonalVehicles(): void {
    this.personalVehiclesService
      .getPersonalVehiclesByUserId(undefined, undefined, {
        httpHeaderAccept: '*/*',
      })
      .subscribe({
        next: (Personalvehicles) => {
          this.personalVehicles = Personalvehicles;
        },
        error: (error) => {
          console.error('Error loading personal vehicles:', error);
        },
      });
  }

  onSubmit(): void {}

  onCancel(): void {}
}
