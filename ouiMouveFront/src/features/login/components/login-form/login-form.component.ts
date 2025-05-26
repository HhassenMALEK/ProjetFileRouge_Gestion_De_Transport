import { Component } from '@angular/core';
import { ButtonComponent } from '../../../../shared/components/button/button.component';
import { InputComponent } from '../../../../shared/components/input/input.component';

@Component({
  selector: 'app-login-form',
  imports: [ButtonComponent, InputComponent],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.scss',
})
export class LoginFormComponent {
  email = '';
  password = '';

  onSubmit() {
    console.log('Login successful:', this.email, this.password);
  }
}
