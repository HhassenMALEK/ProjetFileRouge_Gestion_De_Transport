import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ServiceVehicleControllerService } from '../../../../service/api/serviceVehicleController.service';
import { ServiceVehicleCreateDto } from '../../../../service/model/serviceVehicleCreateDto';
import { ButtonComponent } from '../../../../shared/components/button/button.component';
import { InputComponent } from '../../../../shared/components/input/input.component';
import { SelectComponent } from '../../../../shared/components/select/select.component';
import { ModelControllerService } from '../../../../service/api/modelController.service';
import { SiteControllerService } from '../../../../service/api/siteController.service';
import { ModelDto } from '../../../../service';
import { SiteCreateDto } from '../../../../service/model/siteCreateDto';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create-vehicle-service',
  templateUrl: './create-vehicle-service.component.html',
  styleUrls: ['./create-vehicle-service.component.scss'],
  imports: [FormsModule, ButtonComponent, InputComponent, SelectComponent, CommonModule, ],
})

export class CreateVehicleServiceComponent implements OnInit {
  
  serviceVehicleService = inject(ServiceVehicleControllerService);
  modelControllerService = inject(ModelControllerService);
  siteControllerService = inject(SiteControllerService);
  modelDtos: ModelDto[] = []; 
  siteCreateDtos: SiteCreateDto[] = []; 

   vehicle: ServiceVehicleCreateDto = {
    immatriculation: '',
    seats: undefined,
    status: undefined,
    modelId: undefined,
    modelName: '',
    mark: '',
    siteId: undefined,
    siteName: ''
  };
  
  
ngOnInit(): void {
    // Utilisation d'un subscribe simple comme dans CarpoolingListComponent
    this.modelControllerService.getAllModels(undefined, undefined, { httpHeaderAccept: '*/*' })
      .subscribe({
        next: async (models: any) => {
          // Si la réponse est un Blob, on la parse
          if (models instanceof Blob) {
            const text = await models.text();
            this.modelDtos = JSON.parse(text);
          } else {
            this.modelDtos = models;
          }
          console.log('Modèles reçus:', this.modelDtos);
        },
        error: (err) => {
          console.error('Erreur lors du chargement des modèles', err);
        }
      });
    this.siteControllerService.getAllSites(undefined, undefined, { httpHeaderAccept: '*/*' })
      .subscribe({
        next: async (sites: any) => {
          // Si la réponse est un Blob, on la parse
          if (sites instanceof Blob) {
            const text = await sites.text();
            this.siteCreateDtos = JSON.parse(text);
          } else {
            this.siteCreateDtos = sites;
          }
          console.log('Sites reçus:', this.siteCreateDtos);
        },
        error: (err) => {
          console.error('Erreur lors du chargement des sites', err);
        }
      });
      
  }

  onSubmit() {
    this.serviceVehicleService.createServiceVehicle(this.vehicle).subscribe({
      next: (res) => {
        console.log('Véhicule crée avec succès', res);
        
      },
      error: (err) => {
        console.error('Erreur à la création de Véhicule service ', err);
      }
    });
  }
}
