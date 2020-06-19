import { Component, OnInit } from '@angular/core';
import {UserService} from '../shared/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  username: string;
  constructor(private userService: UserService) {  }

  ngOnInit(): void {
    this.username = localStorage.getItem('username');
  }

  onClick() {

    this.userService.logout().subscribe(res => {
      localStorage.removeItem('jwt');
      localStorage.removeItem('username');
      location.reload();
    }, error => {alert('Błąd wylogowania'); });
  }
}
