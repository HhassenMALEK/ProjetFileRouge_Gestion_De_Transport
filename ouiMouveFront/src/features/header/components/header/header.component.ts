import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MenuComponent } from '../menu/menu.component';

@Component({
  selector: 'app-header',
  imports: [MatIconModule, MenuComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {}
