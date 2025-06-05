import { Component, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
// Services API
import { ServiceVehicleControllerService } from '@openapi/api/serviceVehicleController.service';
import { ModelControllerService } from '@openapi/api/modelController.service';  
import { SiteControllerService } from '@openapi/api/siteController.service';  
import { ModelDto } from '@openapi/index';

// DTOs
import { ServiceVehicleCreateDto } from '@openapi/model/serviceVehicleCreateDto';
import { SiteCreateDto } from '@openapi/model/siteCreateDto';

// Composants partagés
import { ButtonComponent } from '@shared/components/button/button.component';
import { InputComponent } from '@shared/components/input/input.component';
import { SelectComponent } from '@shared/components/select/select.component';

import { ConfirmationPopupComponent } from '@shared/components/confirmation-popup/confirmation-popup.component';
import { ImmatriculationValidator } from '@shared/utils/ImmatriculationValidator';
@Component({
  selector: 'app-create-service-vehicle',
  templateUrl: './create-service-vehicle.component.html',
  styleUrls: ['./create-service-vehicle.component.scss'],

  imports: [
    FormsModule,
    ButtonComponent,
    InputComponent,
    SelectComponent,
    CommonModule,
    ConfirmationPopupComponent,
    ImmatriculationValidator
  ],

})
export class CreateServiceVehicleComponent implements OnInit, OnDestroy {
  // Services
  private readonly router = inject(Router);
  private readonly serviceServiceVehicle = inject(
    ServiceVehicleControllerService
  );
  private readonly modelControllerService = inject(ModelControllerService);
  private readonly siteControllerService = inject(SiteControllerService);

  // Gestion des souscriptions
  private subscriptions: Subscription[] = [];

  // Data for the component
  modelDtos: ModelDto[] = [];
  siteCreateDtos: SiteCreateDto[] = [];
  showConfirmationPopup = signal(false);

  vehicle: ServiceVehicleCreateDto = {
    immatriculation: '',
    seats: 1,
    status: 'ENABLED',
    modelId: undefined,
    siteId: undefined,
  };

  ngOnInit(): void {
    this.loadModels();
    this.loadSites();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  private loadModels(): void {
    const modelsSub = this.modelControllerService
      .getAllModels(undefined, undefined, { httpHeaderAccept: '*/*' })
      .subscribe({
        next: async (models: any) => {
          if (models instanceof Blob) {
            const text = await models.text();
            this.modelDtos = JSON.parse(text);
          } else {
            this.modelDtos = models;
          }
          console.log('Modèles reçus:', this.modelDtos);
        },

        error: (err) =>
          console.error('Erreur lors du chargement des modèles', err),
      });

    this.subscriptions.push(modelsSub);
  }

  private loadSites(): void {
    const sitesSub = this.siteControllerService
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

    this.subscriptions.push(sitesSub);
  }

  onSubmit(): void {
    this.showConfirmationPopup.set(true);
  }
  onAbort(): void {
    this.router.navigate(['..']);
  }

   confirmSubmit = () => {
    // get nbseats from the model
    const selectedModel = this.modelDtos.find(
      (model) => model.id === this.vehicle.modelId
    );
    if (selectedModel) {
      this.vehicle.seats = selectedModel.seatsModel;
    } else {
      console.error('Modèle sélectionné non trouvé');
      return;
    }

    const submitSub = this.serviceServiceVehicle
      .createServiceVehicle(this.vehicle)
      .subscribe({
        next: (res) => {
          console.log('Véhicule créé avec succès', res);
          this.showConfirmationPopup.set(false);
          this.router.navigate(['..']);
        },
        error: (err) => console.error('Erreur à la création du véhicule service', err),
      });
    this.subscriptions.push(submitSub);
    this.showConfirmationPopup.set(false);
  };

  cancelSubmit = () => {
    this.showConfirmationPopup.set(false);
  };



}
