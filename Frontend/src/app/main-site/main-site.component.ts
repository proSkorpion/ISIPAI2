import { Component, OnInit } from '@angular/core';
import { UserService } from '../shared/user.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Product } from '../models/product';
import { ProductService } from '../shared/product.service';
import { Type } from '../models/Type';

@Component({
  selector: 'app-main-site',
  templateUrl: './main-site.component.html',
  styleUrls: ['./main-site.component.css']
})
export class MainSiteComponent implements OnInit
{
  productList: Array<Product> = [];
  typeList: Array<Type> = [];
  username: string;

  constructor(private userService: UserService, private activatedRoute: ActivatedRoute, private carService: ProductService) { }

  ngOnInit(): void {
    this.username = localStorage.getItem('username');
    this.activatedRoute.queryParams.subscribe(
      params => {
        if (params['error'] !== undefined)
        {
          alert(params['error']);
        }
        else
        {
          if((params['token'] !== undefined))
          {
            localStorage.setItem('jwt', params['token']);
            localStorage.setItem('username', params['username']);
            location.assign('/mainSite');
          }
        }
      }
    );

    this.productList = this.activatedRoute.snapshot.data.productList;
    this.typeList = this.activatedRoute.snapshot.data.typeList;
    console.log(this.productList);
  }

  loginByFacebook()
  {
    this.userService.loginByFacebook();
  }

  loginByGoogle()
  {

    this.userService.loginByGoogle();
  }

  loginByDiscord()
  {
    this.userService.loginByDiscord();
  }

}
