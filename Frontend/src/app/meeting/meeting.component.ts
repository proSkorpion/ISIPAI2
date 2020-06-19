import { Component, OnInit } from '@angular/core';
import { MeetingForm } from '../models/MeetingForm';
import { ValidationErrors } from '../models/ValidationErrors';
import {Product} from '../models/product';
import { ProductService } from '../shared/product.service';
import { UserService } from '../shared/user.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-meeting',
  templateUrl: './meeting.component.html',
  styleUrls: ['./meeting.component.css']
})
export class MeetingComponent implements OnInit {
  meetingForm: MeetingForm = {
    name: '',
    surname: '',
    phone: '',
    email: '',
    carName: undefined
  };

  productList: Array<Product> = [];
  car: Product;
  id: number;
  event: string;

  constructor(private productService: ProductService, private userService: UserService, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.productList = this.activatedRoute.snapshot.data.productList;
  }

  findProductInArray(): Product {
    return this.productList.find(e => (e.carMake + ' ' + e.model) === this.meetingForm.carName);
  }

  onSubmit(contactForm) {
    this.car = this.findProductInArray();
  }

  submitForm() {
    console.log(this.meetingForm.carName);
    if (this.isCarChoosen() === true) {
      this.userService.arrangeTestDrive(this.meetingForm).subscribe(
        res => {},
        err => {
          alert('Nie mozna wyslac prosby o jazde testowa');
        }
      );
      alert('Wyslano wiadomosc');
      location.reload();
    }
  }

  isCarChoosen() {
    if (this.meetingForm.carName !== undefined) {
      return true;
    }
    return false;
  }
}
