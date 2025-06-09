import {
  Component,
  OnInit,
  Output,
  EventEmitter,
  inject,
  signal,
  OnDestroy,
  Input,
} from '@angular/core';
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
import { AuthService } from '@shared/service/auth.service';
import { ConfirmationPopupComponent } from '@shared/components/confirmation-popup/confirmation-popup.component';
import { Router } from '@angular/router';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-carpooling-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    InputIconComponent,
    ButtonComponent,
    ConfirmationPopupComponent,
    SelectComponent,
  ],
  templateUrl: './carpooling-form.component.html',
  styleUrls: ['./carpooling-form.component.scss'],
})
export class CarpoolingFormComponent implements OnInit, OnDestroy {
  private readonly router = inject(Router);
  private readonly carPoolingService = inject(CarPoolingControllerService);
  private readonly serviceVehicleService = inject(
    ServiceVehicleControllerService
  );
  private readonly personalVehiclesService = inject(
    PersonalVehicleControllerService
  );
  private readonly siteControllerService = inject(SiteControllerService);
  private readonly authService = inject(AuthService);

  private subscriptions: Subscription[] = [];

  personalVehicles: PersonalVehicleDto[] = [];
  serviceVehicles: ServiceVehicleDto[] = [];
  siteCreateDtos: SiteCreateDto[] = [];
  availableVehicles: { id: number; label: string }[] = [];

  showConfirmationPopup = signal(false);

  departureTime: string = '';

  carpooling: CarPoolingCreateDto = {
    departureSiteId: undefined,
    destinationSiteId: undefined,
    departure: new Date().toISOString().slice(0, 16), // Format pour l'input HTML
    vehicleId: undefined,
    organizerId: this.authService.getUserId() ?? undefined,
  };

  @Output() created = new EventEmitter<CarPoolingCreateDto>();
  @Input() initialData?: CarPoolingCreateDto;

  // Méthode d'initialisation : Chargement des véhicules et sites au démarrage
  ngOnInit(): void {
    // Chargement des données en parallèle avec forkJoin pour optimiser les appels API
    forkJoin([
      this.personalVehiclesService.getPersonalVehiclesByUserId(),
      this.serviceVehicleService.getAllServiceVehicles(),
      this.siteControllerService.getAllSites(),
    ]).subscribe(
      ([personalVehicles, serviceVehicles, sites]) => {
        this.personalVehicles = personalVehicles;
        this.serviceVehicles = serviceVehicles;
        this.siteCreateDtos = sites;
        this.updateAvailableVehicles(); // Met à jour les véhicules disponibles
      },
      (error) => {
        console.error('Erreur lors du chargement des données :', error);
      }
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  private updateAvailableVehicles(): void {
    this.availableVehicles = [
      ...this.personalVehicles.map((v) => ({
        id: v.id!,
        label: `[Perso] ${v.description ?? ''} ${v.color ?? ''}`.trim(),
      })),
      ...this.serviceVehicles.map((v) => ({
        id: v.id!,
        label: `[Service] ${v.model?.mark ?? ''} ${
          v.model?.modelName ?? ''
        }`.trim(),
      })),
    ];
  }

  private isJsonString(str: string): boolean {
    try {
      JSON.parse(str);
      return true;
    } catch (e) {
      return false;
    }
  }

  onSubmit(): void {
    this.showConfirmationPopup.set(true); // Affiche le popup de confirmation
  }

  onAbort(): void {
    this.router.navigate(['/carpooling']); // Redirige vers la liste des covoiturages
  }

  confirmSubmit = (): void => {
    if (
      !this.carpooling.departureSiteId ||
      !this.carpooling.destinationSiteId ||
      !this.carpooling.departure ||
      !this.carpooling.vehicleId
    ) {
      alert('Veuillez remplir tous les champs obligatoires.');
      return;
    }

    const departureDate = new Date(this.carpooling.departure);
    if (isNaN(departureDate.getTime())) {
      alert('Date de départ invalide.');
      return;
    }

    const arrivalDate = new Date(departureDate.getTime() + 60 * 60 * 1000); // Durée de 1 heure

    const carpoolingForApi: CarPoolingCreateDto = {
      ...this.carpooling,
      departure: departureDate.toISOString(),
      arrival: arrivalDate.toISOString(),
      durationInMinutes: 60,
      status: 'IN_PROGRESS',
      distance: 60,
      organizerId: this.authService.getUserId() ?? undefined,
    };
    console.log('📅 Date de départ :', carpoolingForApi.departure);

    console.log('📤 Données envoyées :', JSON.stringify(carpoolingForApi));

    const sub = this.carPoolingService
      .createCarPooling(carpoolingForApi)
      .subscribe({
        next: (res) => {
          console.log('✅ Covoiturage créé :', res);
          this.created.emit(carpoolingForApi); // Émettre l'objet créé
          this.showConfirmationPopup.set(false); // Fermer le popup de confirmation
          this.router.navigate(['/carpooling']); // Redirige vers la liste des covoiturages
        },
        error: async (err) => {
          let message = 'Erreur inconnue';

          if (err.error instanceof Blob) {
            const text = await err.error.text();
            if (this.isJsonString(text)) {
              const parsed = JSON.parse(text);
              message = parsed.message ?? text;
            } else {
              message = text;
            }
          } else if (typeof err.error === 'string') {
            message = err.error;
          } else if (err.error?.message) {
            message = err.error.message;
          } else {
            message = JSON.stringify(err.error ?? err);
          }
          console.error('❌ Erreur création covoiturage :', message);
          alert(`Erreur création covoiturage : ${message}`);
        },
      });

    this.subscriptions.push(sub);
    this.showConfirmationPopup.set(false);
  };

  cancelSubmit = (): void => {
    this.router.navigate(['/carpooling']);
  };
}
