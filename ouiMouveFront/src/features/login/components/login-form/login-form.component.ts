import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthControllerService } from '@openapi/index';
import { ButtonComponent } from '@shared/components/button/button.component';
import { InputComponent } from '@shared/components/input/input.component';
import { TokenService } from '@shared/service/token.service';

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
  private tokenService = inject(TokenService);
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
        next: (response: any) => {
          if (response && response.token) {
            this.tokenService.saveToken(response.token);
            this.router.navigate(['/search/covoit']); // Navigate to the home page after login
          }
        },
        error: (error: any) => {
          console.error('Login failed:', error);
        },
      });
  }
}
