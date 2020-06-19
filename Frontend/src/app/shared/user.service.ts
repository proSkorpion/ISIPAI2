import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from '../models/AuthenticationResponse';
import { AuthenticationRequest } from '../models/AuthenticationRequest';
import { ValidationErrors } from '../models/ValidationErrors';
import { User } from '../models/User';
import { FeedbackData } from '../models/FeedbackData';
import { MeetingForm } from '../models/MeetingForm';
import { OrderDetails } from '../models/OrderDetails';
import { PaypalData } from '../models/PaypalData';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  private BASE_URL = 'https://localhost:443';
  private REDIRECT_URI = 'http://localhost:4200/mainSite';
  private REGISTER_USER_URL = `${this.BASE_URL}/user/register`;
  private CONFIRM_EMAIL_URL = `${this.BASE_URL}/user/confirm/email`;
  private LOGIN_USER = `${this.BASE_URL}/user/login`;
  private SEND_FEEDBACK = `${this.BASE_URL}/user/send/feedback`;
  private LOGIN_BY_FACEBOOK = `${this.BASE_URL}/oauth2/authorize/facebook?redirect_uri=${this.REDIRECT_URI}`;
  private LOGIN_BY_GOOGLE = `${this.BASE_URL}/oauth2/authorize/google?redirect_uri=${this.REDIRECT_URI}`;
  private LOGIN_BY_DISCORD = `${this.BASE_URL}/oauth2/authorize/discord?redirect_uri=${this.REDIRECT_URI}`;
  private ARRANGE_TEST_DRIVE = `${this.BASE_URL}/user/testDrive`;
  private LOGOUT_USER = `${this.BASE_URL}/user/logout`;
  private PAY_FOR_CAR = `${this.BASE_URL}/paypal/pay`;



  login(credentials: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(this.LOGIN_USER, credentials);
  }

  loginByFacebook() {
    location.assign(this.LOGIN_BY_FACEBOOK);
  }

  loginByGoogle() {
    location.assign(this.LOGIN_BY_GOOGLE);
  }

  loginByDiscord() {
    location.assign(this.LOGIN_BY_DISCORD);
  }

  logout(): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('jwt') });
    return this.http.get(this.LOGOUT_USER,{ headers });
  }

  registerUser(user: User): Observable<ValidationErrors> {
    return this.http.post<ValidationErrors>(this.REGISTER_USER_URL, user);
  }

  sendEmailToken(user: User) {
    return this.http.post(this.CONFIRM_EMAIL_URL, user);
  }

  sendFeedback(feedback: FeedbackData) {
    return this.http.post(this.SEND_FEEDBACK, feedback);
  }

  arrangeTestDrive(meetingForm: MeetingForm): Observable<any> {
    return this.http.post(this.ARRANGE_TEST_DRIVE, meetingForm);
  }

  pay_for_car(personDetails: OrderDetails): Observable<PaypalData>
  {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('jwt')});
    return this.http.post<PaypalData>(this.PAY_FOR_CAR, personDetails, {headers});
  }

  isLoggedIn(){
    if(localStorage.getItem('jwt')!==null){
      return true;
    }}

    isNotLoggedIn(){
     if(localStorage.getItem('jwt')===null){
       return true;
     }}


}
