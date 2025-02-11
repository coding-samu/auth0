import {Component, inject, OnInit} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { User } from '../model/user';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatButtonModule, RouterLink],
  template: `
    @if (user) {
      <h2>Welcome, {{user.name}}!</h2>
      <a mat-button color="primary" routerLink="/groups">Manage Groups</a>
      <br/><br/>
      <button mat-raised-button color="primary" (click)="auth.logout()" id="logout">Logout</button>
    } @else {
      <p>Please log in to manage your Groups.</p>
      <button mat-raised-button color="primary" (click)="auth.login()" id="login">Login</button>
    }
  `
})
export class HomeComponent implements OnInit {
  protected auth: AuthService = inject(AuthService);

  protected isAuthenticated!: boolean;
  protected user!: User;

  public async ngOnInit(): Promise<void> {
    this.isAuthenticated = await this.auth.isAuthenticated();
    this.auth.getUser().subscribe((data: User): User => this.user = data);
  }
}
