import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '@shared/components/button/button.component';
import { SelectComponent } from '@shared/components/select/select.component';
import { ModelControllerService } from '@openapi/api/modelController.service';
import { SiteControllerService } from '@openapi/api/siteController.service';
import { ModelDto } from '@openapi/index';
import { SiteCreateDto } from '@openapi/model/siteCreateDto';
import { Subscription } from 'rxjs';
import { ServiceVehicleCreateDto } from '@openapi/model/serviceVehicleCreateDto';

@Component({
  selector: 'app-service-vehicle-search',
  imports: [ FormsModule,
    ButtonComponent,
    SelectComponent],
  templateUrl: './service-vehicle-search.component.html',
  styleUrl: './service-vehicle-search.component.scss'
})
export class ServiceVehicleSearchComponent {

  private readonly modelControllerService = inject(ModelControllerService);
  private readonly siteControllerService = inject(SiteControllerService);

  private subscriptions: Subscription[] = [];

  modelDtos: ModelDto[] = [];
  siteCreateDtos: SiteCreateDto[] = [];

  vehicleFilter: ServiceVehicleCreateDto = {
      immatriculation:undefined ,
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

  onSearch(): void {
    // Implement search logic here
    console.log('Searching with filter:', this.vehicleFilter);
  }


}
