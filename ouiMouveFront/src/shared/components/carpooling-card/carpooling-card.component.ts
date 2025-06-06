import { Component, input, output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import {
  CarPoolingReservationsResponseDTO,
  CarPoolingResponseDto,
} from '@openapi/index';
import { StatusComponent } from '../status/status.component';
import { DateAndCityComponent } from '../date-and-city/date-and-city.component';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-carpooling-card',
  imports: [
    MatIconModule,
    StatusComponent,
    DateAndCityComponent,
    ButtonComponent,
  ],
  templateUrl: './carpooling-card.component.html',
  styleUrl: './carpooling-card.component.scss',
})
export class CarpoolingCardComponent {
  icon = input<string>();
  color = input<string>();
  carpooling = input<
    CarPoolingReservationsResponseDTO | CarPoolingResponseDto
  >();
  btnLabel = input<string>();
  onClick = output<void>();
  dateAndCityColor = input<string>();
}
