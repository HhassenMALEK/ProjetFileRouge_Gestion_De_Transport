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
@Component({
  selector: 'app-create-vehicle-service',
  templateUrl: './create-vehicle-service.component.html',
  styleUrls: ['./create-vehicle-service.component.scss'],

  imports: [
    FormsModule,
    ButtonComponent,
    InputComponent,
    SelectComponent,
    CommonModule,
    ConfirmationPopupComponent,
  ],

})
export class CreateVehicleServiceComponent implements OnInit, OnDestroy {
  // Services injectés
  private readonly router = inject(Router);
  private readonly serviceVehicleService = inject(
    ServiceVehicleControllerService
  );
  private readonly modelControllerService = inject(ModelControllerService);
  private readonly siteControllerService = inject(SiteControllerService);

  // Gestion des souscriptions
  private subscriptions: Subscription[] = [];

  // Données du formulaire
  modelDtos: ModelDto[] = [];
  siteCreateDtos: SiteCreateDto[] = [];
  showConfirmationPopup = signal(false);

  vehicle: ServiceVehicleCreateDto = {
    immatriculation: '',
    seats: undefined,
    status: undefined,
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

  confirmSubmit(): void {
    const submitSub = this.serviceVehicleService
      .createServiceVehicle(this.vehicle)
      .subscribe({
        next: (res) => {
          console.log('Véhicule créé avec succès', res);
          this.router.navigate(['..']); // Retourne à la liste
        },
        error: (err) =>
          console.error('Erreur à la création du véhicule service', err),
      });

    this.subscriptions.push(submitSub);
  }
  cancelSubmit(): void {
    this.showConfirmationPopup.set(false);
  }

  onAbort(): void {
    this.router.navigate(['..']);
  }
}
