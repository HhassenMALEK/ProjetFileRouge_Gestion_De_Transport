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

@Component({
  selector: 'app-carpooling-form',
  standalone: true,
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
export class CarpoolingFormComponent implements OnInit, OnDestroy {
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

  ngOnInit(): void {
    this.loadPersonalVehicles();
    this.loadServiceVehicles();
    this.loadSites();

    if (this.carpooling.departure) {
      const date = new Date(this.carpooling.departure);
      this.departureTime = date.toISOString().substring(11, 16);
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  private loadPersonalVehicles(): void {
    const sub = this.personalVehiclesService
      .getPersonalVehiclesByUserId(undefined, undefined, {
        httpHeaderAccept: '*/*',
      })
      .subscribe({
        next: async (resp: any) => {
          const data =
            resp instanceof Blob ? JSON.parse(await resp.text()) : resp;
          this.personalVehicles = data;
          this.updateAvailableVehicles();
        },
        error: (err) =>
          console.error('Erreur chargement véhicules perso :', err),
      });
    this.subscriptions.push(sub);
  }

  private loadServiceVehicles(): void {
    const sub = this.serviceVehicleService
      .getAllServiceVehicles(undefined, undefined, {
        httpHeaderAccept: '*/*',
      })
      .subscribe({
        next: async (resp: any) => {
          const data =
            resp instanceof Blob ? JSON.parse(await resp.text()) : resp;
          this.serviceVehicles = data;
          this.updateAvailableVehicles();
        },
        error: (err) =>
          console.error('Erreur chargement véhicules service :', err),
      });
    this.subscriptions.push(sub);
  }

  private loadSites(): void {
    const sub = this.siteControllerService
      .getAllSites(undefined, undefined, {
        httpHeaderAccept: '*/*',
      })
      .subscribe({
        next: async (resp: any) => {
          const data =
            resp instanceof Blob ? JSON.parse(await resp.text()) : resp;
          this.siteCreateDtos = data;
          // 📍 Afficher longitude et latitude de chaque site
          data.forEach((site: any) => {
            console.log(
              `Site: ${site.name}, Longitude: ${site.longY}, Latitude: ${site.latX}`
            );
          });
        },
        error: (err) => console.error('Erreur chargement sites :', err),
      });
    this.subscriptions.push(sub);
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

  updateDepartureTime(): void {
    const raw = this.carpooling.departure;
    if (!raw) return;
    const date = new Date(raw);
    if (isNaN(date.getTime())) return;

    if (this.departureTime) {
      const [hours, minutes] = this.departureTime.split(':');
      date.setHours(+hours);
      date.setMinutes(+minutes);
      date.setSeconds(0);
      date.setMilliseconds(0);
    }

    // Format pour l'input HTML
    this.carpooling.departure = date.toISOString().slice(0, 16);
  }

  onSubmit(): void {
    this.showConfirmationPopup.set(true);
  }

  confirmSubmit = (): void => {
    this.updateDepartureTime();

    if (
      !this.carpooling.departureSiteId ||
      !this.carpooling.destinationSiteId ||
      !this.carpooling.departure ||
      !this.carpooling.vehicleId
    ) {
      alert('Veuillez remplir tous les champs obligatoires.');
      return;
    }

    // Créer des dates à partir des valeurs du formulaire
    const departureDate = new Date(this.carpooling.departure);
    if (isNaN(departureDate.getTime())) {
      alert('Date de départ invalide.');
      return;
    }

    const arrivalDate = new Date(departureDate.getTime() + 60 * 60 * 1000);

    // Créer un nouvel objet pour l'envoi à l'API avec les dates au format ISO complet
    const carpoolingForApi: CarPoolingCreateDto = {
      ...this.carpooling,
      departure: departureDate.toISOString(), // Format ISO complet avec Z pour l'API
      arrival: arrivalDate.toISOString(), // Format ISO complet avec Z pour l'API
      durationInMinutes: 60,
      status: 'IN_PROGRESS', // Ajout du statut pour correspondre à l'exemple JSON
      distance: 60, // Ajout de la distance pour correspondre à l'exemple JSON
      organizerId: this.authService.getUserId() ?? undefined,
    };

    console.log('📤 Données envoyées :', JSON.stringify(carpoolingForApi));

    function isJsonString(str: string): boolean {
      try {
        JSON.parse(str);
        return true;
      } catch (e) {
        return false;
      }
    }

    const sub = this.carPoolingService
      .createCarPooling(carpoolingForApi) // Utiliser le nouvel objet pour l'API
      .subscribe({
        next: (res) => {
          console.log('✅ Covoiturage créé :', res);
          this.created.emit(carpoolingForApi); // Émettre l'objet avec les dates au format ISO
          this.showConfirmationPopup.set(false);
        },
        error: async (err) => {
          let message = 'Erreur inconnue';

          if (err.error instanceof Blob) {
            const text = await err.error.text();

            if (isJsonString(text)) {
              const parsed = JSON.parse(text);
              message = parsed.message ?? text;
            } else {
              message = text; // Ici, c'est juste du texte "La date de..."
            }
          } else if (typeof err.error === 'string') {
            message = err.error;
          } else if (err.error?.message) {
            message = err.error.message;
          } else {
            message = JSON.stringify(err.error ?? err);
          }

          console.error('❌ Erreur création covoiturage :', message);
          alert(message);
          this.showConfirmationPopup.set(false);
        },
      });

    this.subscriptions.push(sub);
  };

  cancelSubmit = (): void => {
    this.showConfirmationPopup.set(false);
  };
}
