import { Component, output, input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-confirmation-modal',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  templateUrl: './confirmation-popup.component.html',
  styleUrl: './confirmation-popup.component.scss',
})
export class ConfirmationPopupComponent {
  title = input<string>('Confirmation');
  message = input<string>('Êtes-vous sûr de vouloir continuer ?');
  onConfirm = output<void>();
  onCancel = output<void>();
}
