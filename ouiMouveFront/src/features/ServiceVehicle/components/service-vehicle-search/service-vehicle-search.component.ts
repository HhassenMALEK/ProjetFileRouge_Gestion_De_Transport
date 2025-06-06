import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '@shared/components/button/button.component';
import { SelectComponent } from '@shared/components/select/select.component';
import { ModelControllerService } from '@openapi/api/modelController.service';
import { SiteControllerService } from '@openapi/api/siteController.service';
import { ModelDto } from '@openapi/index';
import { SiteResponseDto } from '@openapi/model/siteResponseDto';
import { Subscription } from 'rxjs';
import { ServiceVehicleDto } from '@openapi/model/serviceVehicleDto';
import { ServiceVehicleControllerService } from '@openapi/api/serviceVehicleController.service';
import { ServiceVehicleFilteringService } from '@shared/service/serviceVehicle-filtering.service';

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
  private readonly serviceVehiclControllereService = inject(ServiceVehicleControllerService);
  private readonly serviceVehicleFilteringService = inject(ServiceVehicleFilteringService);

  private subscriptions: Subscription[] = [];

  modelDtos: ModelDto[] = [];
  siteCreateDtos: SiteResponseDto[] = [];
  serviceVehicleResults: ServiceVehicleDto[] | undefined = undefined;

  vehicleFilter: ServiceVehicleDto = {
      immatriculation:undefined ,
      seats: undefined,
      status: undefined,
      model: undefined,
      site: undefined,
    };
  modelId: number | undefined;
  siteId: number | undefined;

ngOnInit(): void {
  this.loadModels();
  this.loadSites();
  this.onSearch();
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

    if (this.modelId) {
      this.vehicleFilter.model = this.modelDtos.find(
        (model) => model.id === this.modelId
      );
    }
    if (this.siteId) {
      this.vehicleFilter.site = this.siteCreateDtos.find(
        (site) => site.id === this.siteId
      );
    }
    console.log('Searching with filter:', this.vehicleFilter);
    this.serviceVehiclControllereService
      .filterServiceVehicles(
        undefined,
        this.vehicleFilter.model?.modelName || undefined,
        this.vehicleFilter.site?.name || undefined,
        undefined,
      )
      .subscribe({
        next: (response) => {
          this.serviceVehicleResults = response;
          console.log(
            'ServiceVehicle search results from search covoit:',
            this.serviceVehicleResults
          );
          this.serviceVehicleFilteringService.sendServiceVehicle(
            this.serviceVehicleResults || []
          );
        },
        error: (error) => {
          console.error('Error searching for carpooling:', error);
          console.log('Search parameters:', this.vehicleFilter);
        },
      });

  }


}
