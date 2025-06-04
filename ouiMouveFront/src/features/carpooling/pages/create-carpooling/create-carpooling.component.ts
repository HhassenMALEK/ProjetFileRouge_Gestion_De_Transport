import { Component } from '@angular/core';
import { CarpoolingFormComponent } from '../../components/carpooling-form/carpooling-form.component';

@Component({
  selector: 'app-create-carpooling',
  imports: [CarpoolingFormComponent],
  templateUrl: './create-carpooling.component.html',
  styleUrl: './create-carpooling.component.scss',
})
export class CreateCarpoolingComponent {}
