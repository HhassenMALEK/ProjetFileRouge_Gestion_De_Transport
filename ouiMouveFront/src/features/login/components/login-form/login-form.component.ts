import { Component } from '@angular/core';
import { ButtonComponent } from '../../../../shared/components/button/button.component';
import { InputComponent } from '../../../../shared/components/input/input.component';
import { AuthControllerService } from '../../../../api';

@Component({
  selector: 'app-login-form',
  imports: [ButtonComponent, InputComponent],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.scss',
})
export class LoginFormComponent {
  email = '';
  password = '';
  auth;
  constructor(authControllerService: AuthControllerService) {
    // You can inject the AuthControllerService here if needed
    this.auth = authControllerService;
  }
  onSubmit() {
    this.auth
      .login({
        email: this.email,
        password: this.password,
      })
      .subscribe({
        next: (response) => {
          console.log('Login successful:', response);
        },
        error: (error) => {
          console.error('Login failed:', error);
        },
      });
  }
}
