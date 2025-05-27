import { Component } from '@angular/core';
import { SearchBarComponent } from '../search-bar/search-bar.component';
import { CarpoolingCardComponent } from '../carpooling-card/carpooling-card.component';

@Component({
  selector: 'app-carpooling-list',
  imports: [CarpoolingCardComponent, SearchBarComponent],
  templateUrl: './carpooling-list.component.html',
  styleUrl: './carpooling-list.component.scss'
})
export class CarpoolingListComponent {

}
