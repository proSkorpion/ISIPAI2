import { Component, OnInit } from '@angular/core';
import { User } from '../models/User';
import { UserService } from '../shared/user.service';
import { ValidationErrors } from '../models/ValidationErrors';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User = {
    name: '',
    surname: '',
    username: '',
    email: '',
    password: ''
  };

  confirmPassword = '';

  validationErrors: ValidationErrors = {
    nameError: null,
    surnameError: null,
    emailError: null,
    usernameError: null,
    passwordError: null,
  };

  constructor(private userSerice: UserService) { }

  ngOnInit(): void {
  }

  registerForm() {
    this.setEmptyErrors();
    const userReal = this.user;
    this.userSerice.registerUser(this.user).subscribe(
      res => {
        this.validationErrors = res;
        if (this.isGoodCredentails() === true) {
          this.userSerice.sendEmailToken(userReal).subscribe(
            res => {}
          );
          alert('Na twoja skrzynke pocztowa został wysłany email z potwierdzeniem twojego konta');
          location.reload();
        }
      },
      err => {
        alert('Nie mozna zarejestrowac uzytkownika');
      }
    );
  }

  isGoodCredentails(): boolean {
    if (this.validationErrors.emailError === null &&
      this.validationErrors.usernameError === null &&
      this.validationErrors.passwordError === null &&
      this.validationErrors.nameError === null &&
      this.validationErrors.surnameError === null)
      {
        return true;
      }
      else
      {
        return false;
      }
  }

  setEmptyErrors() {
    this.validationErrors.nameError = null;
    this.validationErrors.surnameError = null;
    this.validationErrors.emailError = null;
    this.validationErrors.usernameError = null;
    this.validationErrors.passwordError = null;
  }

}
