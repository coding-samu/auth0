import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router, RouterLink} from '@angular/router';
import { map, of, switchMap } from 'rxjs';
import { Group } from '../../model/group';
import { Event } from '../../model/event';
import { HttpClient } from '@angular/common/http';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { MatNativeDateModule } from '@angular/material/core';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-group-edit',
  standalone: true,
  imports: [
    FormsModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatIconModule,
    MatNativeDateModule,
    MatTooltipModule,
    RouterLink
  ],
  templateUrl: './group-edit.component.html',
  styleUrl: './group-edit.component.css'
})
export class GroupEditComponent implements OnInit {
  private route: ActivatedRoute = inject(ActivatedRoute);
  private router: Router = inject(Router);
  private http: HttpClient = inject(HttpClient);
  public group!: Group;
  public feedback: any = {};

  public ngOnInit(): void {
    this.route.params.pipe(
      map((p: Params) => p['id']),
      switchMap(id => {
        if (id === 'new') {
          return of(new Group());
        }
        return this.http.get<Group>(`api/group/${id}`);
      })
    ).subscribe({
      next: (group: Group): void => {
        this.group = group;
        this.feedback = {};
      },
      error: (): void => {
        this.feedback = {type: 'warning', message: 'Error loading'};
      }
    });
  }

  public save(): void {
    const id: number | null = this.group.id;
    const method: 'put' | 'post' = id ? 'put' : 'post';

    this.http[method](`/api/group${id ? '/' + id : ''}`, this.group).subscribe({
      next: (): void => {
        this.feedback = {type: 'success', message: 'Save was successful!'};
        setTimeout(async (): Promise<void> => {
          await this.router.navigate(['/groups']);
        }, 1000);
      },
      error: (): void => {
        this.feedback = {type: 'error', message: 'Error saving'};
      }
    });
  }

  public async cancel(): Promise<void> {
    await this.router.navigate(['/groups']);
  }

  public addEvent(): void {
    this.group.events.push(new Event());
  }

  public removeEvent(index: number): void {
    this.group.events.splice(index, 1);
  }
}
