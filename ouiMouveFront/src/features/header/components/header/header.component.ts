import { Component, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MenuComponent } from '../menu/menu.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [MatIconModule, MenuComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  router = inject(Router);
}
