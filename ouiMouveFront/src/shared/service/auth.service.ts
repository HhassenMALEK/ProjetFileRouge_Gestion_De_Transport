import { inject, Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserControllerService, UserDto } from '../../api';
interface DecodedToken {
  sub: string; // Email de l'utilisateur
  userId?: number;
  role?: string[];
  iat?: number; // Date d'expiration du token
  exp?: number; // Date d'expiration du token
}
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly TOKEN_KEY = 'jwt_token';
  private userSubject = new BehaviorSubject<UserDto | null>(null);
  public user$: Observable<UserDto | null> = this.userSubject.asObservable();
  private userService = inject(UserControllerService);
  constructor() {}
  private async loadUserOnInit(): Promise<void> {
    const token = this.getToken();
    if (token && !this.userSubject.value) {
      const decodedToken = jwtDecode<DecodedToken>(token);
      if (decodedToken.userId) {
        this.userService.getUserById(decodedToken.userId).subscribe({
          next: (user) => {
            this.userSubject.next(user);
          },
          error: (error) => {
            console.error('Error loading user:', error);
            this.userSubject.next(null);
          },
        });
      }
    }
  }
  saveToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  removeToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}
