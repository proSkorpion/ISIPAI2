import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { ProductService } from '../shared/product.service';
import { OrderDetails} from '../models/OrderDetails';
import {Product} from '../models/product';
import { UserService } from '../shared/user.service';
import { PaypalData } from '../models/PaypalData';

@Component({
  selector: 'app-details-client',
  templateUrl: './details-client.component.html',
  styleUrls: ['./details-client.component.css']
})
export class DetailsClientComponent implements OnInit {
  status: boolean;
  statusPesel: boolean;
  id: number;
  paypalData: PaypalData;

  personDetails: OrderDetails = {
    username: '',
    address: '',
    pesel: '',
    total: 0,
    description: '',
    carsId: ''
  };
  constructor(private productService: ProductService, private router: Router,
    private activatedRoute: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
    this.status = false;
    this.statusPesel = false;
    this.personDetails.username = localStorage.getItem('username');
    this.activatedRoute.queryParams.subscribe(
      params => {
        this.personDetails.carsId = params['idCar'].toString();
        this.personDetails.total = params['car'];
      }
    );
  }


  submitForm() {
    console.log(this.personDetails);
    this.userService.pay_for_car(this.personDetails).subscribe(
      res => {
        this.paypalData = res;
        if(this.paypalData.link !== null)
        {
          location.assign(this.paypalData.link);
        }
        else
        {
          alert('Podales bledne dane');
        }
      },
      err => {
        alert('Nie udalo sie dokonac platnosci');
      }
    )
  }

  check() {
    this.checkPESEL();
    console.log(this.statusPesel);
    if (this.personDetails.address !== '' && this.personDetails.pesel?.toString() !== '' &&
        this.personDetails.description !== '' && this.statusPesel === true)
    {
      this.status = true;
    }
  }

  checkPESEL() {
    let stringPesel;
    let digits;
    stringPesel = this.personDetails.pesel?.toString();
    digits = stringPesel?.split('');

    if(stringPesel !== undefined) {
      if ((parseInt(stringPesel?.substring(4, 6)) > 31) || (parseInt(stringPesel?.substring(2, 4)) > 12))
        return this.statusPesel = false;


      var checksum = (1 * parseInt(digits[0]) + 3 * parseInt(digits[1]) + 7 * parseInt(digits[2]) + 9 * parseInt(digits[3]) + 1 * parseInt(digits[4]) + 3 * parseInt(digits[5]) + 7 * parseInt(digits[6]) + 9 * parseInt(digits[7]) + 1 * parseInt(digits[8]) + 3 * parseInt(digits[9])) % 10;
      if (checksum === 0) checksum = 10;
      checksum = 10 - checksum;

      this.statusPesel = (parseInt(digits[10]) === checksum);
    }



  }
}
