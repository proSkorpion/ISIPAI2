import { Component, OnInit } from '@angular/core';
import { UserService } from '../shared/user.service';
import { AuthenticationRequest } from '../models/AuthenticationRequest';
import { AuthenticationResponse } from '../models/AuthenticationResponse';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  authenticationRequest: AuthenticationRequest = {
    username: '',
    password: ''
  };

  authenticationResponse: AuthenticationResponse;


  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  loginForm() {
    this.userService.login(this.authenticationRequest).subscribe(
      res => {
        this.authenticationResponse = res;
        if (this.isGoodCredentials()) {
          localStorage.setItem('jwt', this.authenticationResponse.jwt);
          localStorage.setItem('username', this.authenticationResponse.username);
          location.assign('/mainSite');
        }
      },
      err => {
        alert('Wystapil blad podczas proby logowania. Spróbuj ponownie poźniej');
      }
    );
  }

  isGoodCredentials(): boolean {
    if (this.authenticationResponse?.jwt === 'Nieprawidłowe hasło lub login') {
      return false;
    } else {
      return true;
    }
  }

}
