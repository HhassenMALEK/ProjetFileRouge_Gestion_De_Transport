import { Component } from '@angular/core';
import { OngletComponent } from './components/onglet/onglet.component';
import { SearchCovoitComponent } from './components/search-covoit/search-covoit.component';
import { SearchResaComponent } from './components/search-resa/search-resa.component';

@Component({
  selector: 'app-search-bar',
  imports: [OngletComponent, SearchCovoitComponent, SearchResaComponent],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.scss',
})
export class SearchBarComponent {
  activeOngletIndex: number = 0;
  handleOnglet(index: number) {
    this.activeOngletIndex = index;
  }
}
