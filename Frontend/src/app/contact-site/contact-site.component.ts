import { Component, OnInit } from '@angular/core';
import { FeedbackData } from '../models/FeedbackData';
import { UserService } from '../shared/user.service';

@Component({
  selector: 'app-contact-site',
  templateUrl: './contact-site.component.html',
  styleUrls: ['./contact-site.component.css']
})
export class ContactSiteComponent implements OnInit {

  feedbackData: FeedbackData = {
    subject: '',
    email: '',
    content: ''
  };

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  submitForm() {
    this.userService.sendFeedback(this.feedbackData).subscribe(
      res => {},
      err => {
        alert('Nie udalo sie wyslac wiadomosci');
      }
    );
    alert('Wyslano wiadomosc');
    location.reload();
  }

}
