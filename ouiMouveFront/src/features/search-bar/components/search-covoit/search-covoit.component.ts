import { Component } from '@angular/core';
import { InputIconComponent } from '../../../../shared/components/input-icon/input-icon.component';

@Component({
  selector: 'app-search-covoit',
  imports: [InputIconComponent],
  templateUrl: './search-covoit.component.html',
  styleUrl: './search-covoit.component.scss',
})
export class SearchCovoitComponent {
  departure = '';
  arrival = '';
  date = '';
  passanger = 1;
}
