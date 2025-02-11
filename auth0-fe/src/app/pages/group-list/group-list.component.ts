import { Component } from '@angular/core';
import { Group } from '../../model/group';
import { HttpClient } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-group-list',
  standalone: true,
  imports: [RouterLink, MatButtonModule, MatTableModule, MatIconModule, DatePipe],
  templateUrl: './group-list.component.html',
  styleUrl: './group-list.component.css'
})
export class GroupListComponent {
  title: string = 'Group List';
  loading: boolean = true;
  groups: Group[] = [];
  displayedColumns: string[] = ['id', 'name', 'events', 'actions'];
  feedback: any = {};

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.http.get<Group[]>('api/groups').subscribe((data: Group[]): void => {
      this.groups = data;
      this.loading = false;
      this.feedback = {};
    });
  }

  delete(group: Group): void {
    if (confirm(`Are you sure you want to delete ${group.name}?`)) {
      this.http.delete(`api/group/${group.id}`).subscribe({
        next: (): void => {
          this.feedback = {type: 'success', message: 'Delete was successful!'};
          setTimeout((): void => {
            this.ngOnInit();
          }, 1000);
        },
        error: (): void => {
          this.feedback = {type: 'warning', message: 'Error deleting.'};
        }
      });
    }
  }
}
