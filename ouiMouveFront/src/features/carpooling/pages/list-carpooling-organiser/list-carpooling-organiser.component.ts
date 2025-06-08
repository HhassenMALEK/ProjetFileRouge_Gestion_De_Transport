import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import {
  CarPoolingControllerService,
  CarPoolingResponseDto,
} from '@openapi/index';
import { CarpoolingCardComponent } from '@shared/components/carpooling-card/carpooling-card.component';
import { AuthService } from '@shared/service/auth.service';
import { CarPoolingFilteringService } from '@shared/service/carpooling-filtering.service';

@Component({
  selector: 'app-list-carpooling-organiser',
  standalone: true,
  imports: [CarpoolingCardComponent, CommonModule, RouterModule],
  templateUrl: './list-carpooling-organiser.component.html',
  styleUrl: './list-carpooling-organiser.component.scss',
})
export class ListCarpoolingOrganiserComponent implements OnInit {
  carPoolings: CarPoolingResponseDto[] = []; // Liste des covoiturages
  carPoolingFilteringService = inject(CarPoolingFilteringService);
  authService = inject(AuthService);
  carPoolingService = inject(CarPoolingControllerService);
  loading = false;
  error = false;

  // Déclarez l'ID de l'organisateur, récupéré à partir de l'authService
  organizerId: number | undefined;

  ngOnInit() {
    // Récupération de l'ID de l'organisateur depuis le service Auth
    this.organizerId = this.authService.getUserId() || undefined;

    // Si l'ID de l'organisateur est récupéré, chargez les covoiturages
    if (this.organizerId) {
      this.loadCarPoolingsByOrganizer();
    } else {
      console.error("Impossible de récupérer l'ID de l'organisateur");
      this.error = true;
    }
  }

  // Méthode pour charger les covoiturages de l'organisateur
  loadCarPoolingsByOrganizer(): void {
    // Initialiser l'état de chargement
    this.loading = true;
    this.error = false;

    // Appel au service pour récupérer les covoiturages
    this.carPoolingService
      .filterByCriteria(
        this.organizerId, // Passer l'ID de l'organisateur
        undefined, // status (non filtré pour le moment)
        undefined, // startDate (non filtré)
        undefined, // endDate (non filtré)
        undefined, // nameDeparture (non filtré)
        undefined, // nameDestination (non filtré)
        undefined, // vehicleId (non filtré)
        undefined, // capacity (non filtré)
        undefined // options (non filtré)
      )
      .subscribe({
        // Si la réponse est reçue avec succès
        next: async (response: any) => {
          try {
            // Vérifier si la réponse est un Blob et la traiter si nécessaire
            const data =
              response instanceof Blob
                ? JSON.parse(await response.text())
                : response;

            // Stocker les données reçues dans la variable carPoolings
            this.carPoolings = data;
            console.log(
              `Covoiturages de l'organisateur ${this.organizerId}:`,
              this.carPoolings
            );
          } catch (error) {
            console.error(
              'Erreur lors du traitement des données de réponse:',
              error
            );
            this.error = true;
          } finally {
            // Arrêter l'état de chargement à la fin
            this.loading = false;
          }
        },
        // Si une erreur survient lors de l'appel API
        error: (error: any) => {
          console.error('Erreur lors du chargement des covoiturages:', error);
          this.error = true;
          this.loading = false;
        },
      });
  }
}
