import { Component, OnInit, Output, EventEmitter, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { InputIconComponent } from '../../../../shared/components/input-icon/input-icon.component';
import { ButtonComponent } from '../../../../shared/components/button/button.component';
import {
  CarPoolingControllerService,
  CarPoolingCreateDto,
  ServiceVehicleControllerService, // Supposons que c'est le service pour les véhicules
} from '../../../../service';

@Component({
  selector: 'app-carpooling-form',
  imports: [CommonModule, FormsModule, InputIconComponent, ButtonComponent],
  templateUrl: './carpooling-form.component.html',
  styleUrl: './carpooling-form.component.scss',
  standalone: true,
})
export class CarpoolingFormComponent implements OnInit {
  carPoolingService = inject(CarPoolingControllerService);
  vehicleService = inject(ServiceVehicleControllerService);

  @Output() submitted = new EventEmitter<CarPoolingCreateDto>();

  carpoolingParameters = {
    siteDeparture: '',
    siteDestination: '',
    startDate: '',
    startTime: '',
    capacity: 1,
    vehicleId: '',
  };

  vehicles: { id: string; label: string }[] = [];

  ngOnInit(): void {
    this.loadVehicles();
  }

  loadVehicles(): void {}

  onSubmit(): void {}

  onCancel(): void {
    this.carpoolingParameters = {
      siteDeparture: '',
      siteDestination: '',
      startDate: '',
      startTime: '',
      capacity: 1,
      vehicleId: '',
    };
  }
}
