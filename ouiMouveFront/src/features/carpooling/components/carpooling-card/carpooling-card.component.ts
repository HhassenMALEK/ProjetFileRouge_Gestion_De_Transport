import { Component, input } from '@angular/core';
import { StatusComponent } from '../../../../shared/components/status/status.component';
import { CarPoolingResponseDto } from '../../../../api';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-carpooling-card',
  imports: [StatusComponent, MatIconModule],
  templateUrl: './carpooling-card.component.html',
  styleUrl: './carpooling-card.component.scss'
})
export class CarpoolingCardComponent {
  carpooling = input<CarPoolingResponseDto>();

}
