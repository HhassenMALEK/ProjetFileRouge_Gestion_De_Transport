import { Component } from '@angular/core';
import { OngletComponent } from './components/onglet/onglet.component';

@Component({
  selector: 'app-search-bar',
  imports: [OngletComponent],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.scss',
})
export class SearchBarComponent {
  activeOngletIndex: number = 0;
  handleOnglet(index: number) {
    this.activeOngletIndex = index;
  }
}
