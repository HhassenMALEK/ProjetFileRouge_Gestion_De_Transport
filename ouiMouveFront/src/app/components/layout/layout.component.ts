import { Component } from '@angular/core';
import { HeaderComponent } from '../../../features/header/components/header/header.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-layout',
  imports: [HeaderComponent, RouterModule],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss',
})
export class LayoutComponent {}
