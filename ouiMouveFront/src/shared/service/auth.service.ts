import { inject, Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserControllerService, UserDto } from '../../api';
import { TokenService } from './token.service';
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
  private tokenService = inject(TokenService);
  private userSubject = new BehaviorSubject<UserDto | null>(null);
  public user$: Observable<UserDto | null> = this.userSubject.asObservable();
  private userService = inject(UserControllerService);
  constructor() {
    this.loadUserOnInit();
  }
  private async loadUserOnInit(): Promise<void> {
    const token = this.tokenService.getToken();
    if (token && !this.userSubject.value) {
      const decodedToken = jwtDecode<DecodedToken>(token);
      if (decodedToken.userId) {
        this.userService.getUserById(decodedToken.userId).subscribe({
          next: (user) => {
            this.userSubject.next(user);
            console.log('User loaded from token:', user);
          },
          error: (error) => {
            console.error('Error loading user:', error);
            this.userSubject.next(null);
          },
        });
      }
    }
  }

  logout(): void {
    this.tokenService.removeToken();
    this.userSubject.next(null); // Clear user data on logout
  }

  isAuthenticated(): boolean {
    return !!this.tokenService.getToken();
  }

  isAdmin(): boolean {
    const token = this.tokenService.getToken();
    if (!this.isAuthenticated() || !token) return false;
    const decodedToken = jwtDecode<DecodedToken>(token);
    return decodedToken.role ? decodedToken.role.includes('ADMIN') : false;
  }
}
