import { Component, inject } from '@angular/core';
import { ButtonComponent } from '../../../../shared/components/button/button.component';
import { InputComponent } from '../../../../shared/components/input/input.component';
import { AuthControllerService } from '../../../../api';
import { AuthService } from '../../../../shared/service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-form',
  imports: [ButtonComponent, InputComponent],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.scss',
})
export class LoginFormComponent {
  email = 'admin@ouimouve.fr';
  password = 'test';
  private authApiService = inject(AuthControllerService);
  private authService = inject(AuthService);
  private router = inject(Router);
  onSubmit() {
    this.authApiService
      .login(
        {
          email: this.email,
          password: this.password,
        },
        'body',
        false, // reportProgress (assuming this is the next parameter, defaults to false)
        { httpHeaderAccept: 'application/json' as any } // options
      )
      .subscribe({
        next: (response) => {
          if (response && response.token) {
            this.authService.saveToken(response.token);
            this.router.navigate(['/search']); // Navigate to the home page after login
          }
        },
        error: (error) => {
          console.error('Login failed:', error);
        },
      });
  }
}
