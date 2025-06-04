import { Component, OnInit, Output, EventEmitter, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { InputIconComponent } from '@shared/components/input-icon/input-icon.component';
import { ButtonComponent } from '@shared/components/button/button.component';
import { CarPoolingControllerService } from '@openapi/api/carPoolingController.service';
import { ServiceVehicleControllerService } from '@openapi/api/serviceVehicleController.service';
import { CarPoolingCreateDto } from '@openapi/model/carPoolingCreateDto';
import { ServiceVehicleDto } from '@openapi/model/serviceVehicleDto';
import { SelectComponent } from '@shared/components/select/select.component';
import { PersonalVehicleControllerService } from '@openapi/api/personalVehicleController.service';
import { PersonalVehicleDto } from '@openapi/model/personalVehicleDto';
import { SiteControllerService, SiteCreateDto } from '@openapi/index';
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
  private readonly carPoolingService = inject(CarPoolingControllerService);
  private readonly serviceVehicleService = inject(
    ServiceVehicleControllerService
  );
  private readonly personalVehiclesService = inject(
    PersonalVehicleControllerService
  );
  private readonly siteControllerService = inject(SiteControllerService);

  private subscriptions: Subscription[] = [];

  serviceVehicle: ServiceVehicleDto[] = [];
  personalVehicles: PersonalVehicleDto[] = [];
  siteCreateDtos: SiteCreateDto[] = [];

  carpooling: CarPoolingCreateDto = {
    departureSiteId: undefined,
    destinationSiteId: undefined,
    departure: '',
    vehicleId: undefined,
  };

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  isSubmitting = false; //États internes / événements
  @Output() cancel = new EventEmitter<void>();
  @Output() created = new EventEmitter<CarPoolingCreateDto>();

  ngOnInit(): void {
    this.loadPersonalVehicles();
    this.loadSites();
  }

  private loadPersonalVehicles(): void {
    const sub = this.personalVehiclesService
      .getPersonalVehiclesByUserId(undefined, undefined, {
        httpHeaderAccept: '*/*',
      })
      .subscribe({
        next: async (resp: any) => {
          if (resp instanceof Blob) {
            const text = await resp.text();
            this.personalVehicles = JSON.parse(text);
          } else {
            this.personalVehicles = resp;
          }
          console.log('Liste récupérée :', this.personalVehicles);
        },
        error: (err) => {
          console.error('Erreur chargement véhicules perso :', err);
        },
      });
    this.subscriptions.push(sub);
  }

  // private loadServiceVehicles(): void {
  //   const sub = this.serviceVehicleService
  //     .getServiceVehicle(undefined, undefined, { httpHeaderAccept: '*/*' })
  //     .subscribe({
  //       next: async (resp: any) => {
  //         if (resp instanceof Blob) {
  //           const text = await resp.text();
  //           this.serviceVehicle = JSON.parse(text);
  //         } else {
  //           this.serviceVehicle = resp;
  //         }
  //         console.log('Liste récupérée :', this.serviceVehicle);
  //       },
  //       error: (err) => {
  //         console.error('Erreur chargement véhicules de service :', err);
  //       },
  //     });
  //   this.subscriptions.push(sub);
  // }

  private loadSites(): void {
    const Sub = this.siteControllerService
      .getAllSites(undefined, undefined, { httpHeaderAccept: '*/*' })
      .subscribe({
        next: async (sites: any) => {
          if (sites instanceof Blob) {
            const text = await sites.text();
            this.siteCreateDtos = JSON.parse(text);
          } else {
            this.siteCreateDtos = sites;
          }
          console.log('Sites reçus:', this.siteCreateDtos);
        },

        error: (err) =>
          console.error('Erreur lors du chargement des sites', err),
      });

    this.subscriptions.push(Sub);
  }

  onSubmit(): void {}

  onCancel(): void {}
}
