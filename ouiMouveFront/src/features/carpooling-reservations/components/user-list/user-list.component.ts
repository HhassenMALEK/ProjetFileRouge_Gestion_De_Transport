import { Component, input } from '@angular/core';

@Component({
  selector: 'app-user-list',
  imports: [],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss',
})
export class UserListComponent {
  users = input<string[] | undefined>(undefined);
}
