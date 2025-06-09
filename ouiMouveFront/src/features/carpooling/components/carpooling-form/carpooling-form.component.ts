import { CommonModule } from '@angular/common';
import {
  Component,
  EventEmitter,
  inject,
  Input,
  OnDestroy,
  OnInit,
  Output,
  signal,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CarPoolingControllerService } from '@openapi/api/carPoolingController.service';
import { PersonalVehicleControllerService } from '@openapi/api/personalVehicleController.service';
import { ServiceVehicleControllerService } from '@openapi/api/serviceVehicleController.service';
import { SiteControllerService, SiteCreateDto } from '@openapi/index';
import { CarPoolingCreateDto } from '@openapi/model/carPoolingCreateDto';
import { PersonalVehicleDto } from '@openapi/model/personalVehicleDto';
import { ServiceVehicleDto } from '@openapi/model/serviceVehicleDto';
import { ButtonComponent } from '@shared/components/button/button.component';
import { ConfirmationPopupComponent } from '@shared/components/confirmation-popup/confirmation-popup.component';
import { SelectComponent } from '@shared/components/select/select.component';
import { AuthService } from '@shared/service/auth.service';
import { forkJoin, Subscription } from 'rxjs';

@Component({
  selector: 'app-carpooling-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ButtonComponent,
    ConfirmationPopupComponent,
    SelectComponent,
  ],
  templateUrl: './carpooling-form.component.html',
  styleUrls: ['./carpooling-form.component.scss'],
})
export class CarpoolingFormComponent implements OnInit, OnDestroy {
  // Injection des services nécessaires pour la gestion des données
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

  private subscriptions: Subscription[] = []; // Stocke les abonnements à un observable pour pouvoir les désabonner plus tard.

  // Données du formulaire
  personalVehicles: PersonalVehicleDto[] = [];
  serviceVehicles: ServiceVehicleDto[] = [];
  siteCreateDtos: SiteCreateDto[] = [];
  availableVehicles: { id: number; label: string }[] = [];

  // Indicateur pour afficher le popup de confirmation
  showConfirmationPopup = signal(false);

  // Définition des champs du formulaire
  departureTime: string = '';
  carpooling: CarPoolingCreateDto = {
    departureSiteId: undefined,
    destinationSiteId: undefined,
    departure: new Date().toISOString().slice(0, 16), // Format pour l'input HTML
    vehicleId: undefined,
    organizerId: this.authService.getUserId() ?? undefined,
  };

  @Output() created = new EventEmitter<CarPoolingCreateDto>(); // Émet l'objet de covoiturage créé
  @Input() initialData?: CarPoolingCreateDto; // Données initiales envoyées au composant

  // Méthode d'initialisation
  ngOnInit(): void {
    // Chargement des données en parallèle avec forkJoin pour optimiser les appels API
    forkJoin([
      this.personalVehiclesService.getPersonalVehiclesByUserId(), // Appel API pour les véhicules personnels
      this.serviceVehicleService.getAllServiceVehicles(), // Appel API pour les véhicules de service
      this.siteControllerService.getAllSites(), // Appel API pour les sites
    ]).subscribe(
      ([personalVehicles, serviceVehicles, sites]) => {
        this.personalVehicles = personalVehicles;
        this.serviceVehicles = serviceVehicles;
        this.siteCreateDtos = sites;
        this.updateAvailableVehicles(); // Met à jour la liste des véhicules disponibles
      },
      (error) => {
        console.error('Erreur lors du chargement des données :', error);
      }
    );
  }

  // Méthode de nettoyage (désabonnement des observables)
  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe()); // Désabonne tous les abonnements lorsque le composant est détruit
  }

  // Met à jour la liste des véhicules disponibles en combinant les véhicules personnels et de service
  private updateAvailableVehicles(): void {
    this.availableVehicles = [
      ...this.personalVehicles.map((v) => ({
        id: v.id!,
        label: `vehicule Personelle: ${v.description ?? ''} ${
          v.color ?? ''
        }`.trim(),
      })),
      ...this.serviceVehicles.map((v) => ({
        id: v.id!,
        label: `Vehicule de Service: ${v.model?.mark ?? ''} ${
          v.model?.modelName ?? ''
        }`.trim(),
      })),
    ];
  }

  // Méthode déclenchée lors de la soumission du formulaire
  onSubmit(): void {
    this.showConfirmationPopup.set(true); // Affiche le popup de confirmation avant soumission
  }

  // Méthode pour annuler la soumission et revenir à la liste des covoiturages
  onAbort(): void {
    this.router.navigate(['/carpooling']); // Redirige vers la page des covoiturages
  }

  // Méthode pour confirmer la soumission du covoiturage
  confirmSubmit = (): void => {
    // Vérifie si tous les champs obligatoires sont remplis
    if (
      !this.carpooling.departureSiteId ||
      !this.carpooling.destinationSiteId ||
      !this.carpooling.departure ||
      !this.carpooling.vehicleId
    ) {
      alert('Veuillez remplir tous les champs obligatoires.');
      return;
    }

    // Validation de la date de départ
    const departureDate = new Date(this.carpooling.departure);
    if (isNaN(departureDate.getTime())) {
      alert('Date de départ invalide.');
      return;
    }

    // Calcul de la date d'arrivée (ajoute 1 heure)
    const arrivalDate = new Date(departureDate.getTime() + 60 * 60 * 1000); // Durée de 1 heure

    // Préparation des données à envoyer à l'API
    const carpoolingForApi: CarPoolingCreateDto = {
      ...this.carpooling,
      departure: departureDate.toISOString(),
      arrival: arrivalDate.toISOString(),
      durationInMinutes: 60,
      status: 'IN_PROGRESS',
      distance: 60,
      organizerId: this.authService.getUserId() ?? undefined,
    };

    // Envoi de la demande à l'API pour créer un covoiturage
    const sub = this.carPoolingService
      .createCarPooling(carpoolingForApi)
      .subscribe({
        next: (res) => {
          console.log('✅ Covoiturage créé :', res);
          this.created.emit(carpoolingForApi); // Émet l'objet créé vers le parent
          this.showConfirmationPopup.set(false); // Ferme le popup de confirmation
          this.router.navigate(['/carpooling']); // Redirige vers la liste des covoiturages
        },
        error: async (err) => {
          let message = 'Erreur inconnue';

          if (err.error instanceof Blob) {
          } else if (typeof err.error === 'string') {
            message = err.error;
          } else if (err.error?.message) {
            message = err.error.message;
          } else {
            message = JSON.stringify(err.error ?? err);
          }
          console.error('❌ Erreur création covoiturage :', message);
          alert('Erreur création covoiturage');
        },
      });

    this.subscriptions.push(sub); // Ajoute l'abonnement à la liste pour pouvoir le désabonner plus tard
    this.showConfirmationPopup.set(false); // Ferme le popup de confirmation
  };

  // Annule la soumission et redirige vers la liste des covoiturages
  cancelSubmit = (): void => {
    this.router.navigate(['/carpooling']);
  };
}
