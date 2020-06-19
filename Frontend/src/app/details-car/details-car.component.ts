import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Product } from '../models/product';
import { ProductService } from '../shared/product.service';

@Component({
  selector: 'app-details-car',
  templateUrl: './details-car.component.html',
  styleUrls: ['./details-car.component.css']
})

export class DetailsCarComponent implements OnInit
{
  productList: Array<Product>;
  car: Product;
  id: number;

  constructor(private productService: ProductService, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {

    this.productList = this.activatedRoute.snapshot.data.productList;
    this.activatedRoute.queryParams.subscribe(
      params => {
        this.id = params['idCar'];
      }
    );
    this.car = this.findProductInArray();
  }

  findProductInArray(): Product {
     return this.productList.find( result => result.carId.toString() === this.id.toString());
  }

  parsePriceToString()
  {
    return this.car?.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
  }

}
