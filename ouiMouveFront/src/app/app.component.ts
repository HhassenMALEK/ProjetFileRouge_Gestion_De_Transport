import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CarpoolingListComponent } from '../features/carpooling/components/carpooling-list/carpooling-list.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CarpoolingListComponent ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = 'ouiMouveFront';
}
