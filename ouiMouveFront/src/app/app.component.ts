import { Component } from '@angular/core';
import { LayoutComponent } from './components/layout/layout.component';
import { CreateCarpoolingComponent } from '../features/carpooling/pages/create-carpooling/create-carpooling.component';

@Component({
  selector: 'app-root',
  imports: [LayoutComponent, CreateCarpoolingComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = 'ouiMouveFront';
}
