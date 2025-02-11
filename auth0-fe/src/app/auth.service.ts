import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import { BehaviorSubject, lastValueFrom, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { User } from './model/user';

const headers: HttpHeaders = new HttpHeaders().set('Accept', 'application/json');

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  $authenticationState: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient, private location: Location) {
  }

  getUser(): Observable<User> {
    return this.http.get<User>('/api/user', {headers}, )
      .pipe(map((response: User): User => {
          if (response !== null) {
            this.$authenticationState.next(true);
          }
          return response;
        })
      );
  }

  async isAuthenticated(): Promise<boolean> {
    const user: User = await lastValueFrom(this.getUser());
    return user !== null;
  }

  login(): void {
    location.href = `${location.origin}${this.location.prepareExternalUrl('oauth2/authorization/okta')}`;
  }

  logout(): void {
    this.http.post('/api/logout', {}, { withCredentials: true }).subscribe((response: any): void => {
      location.href = response.logoutUrl;
    });
  }
}
