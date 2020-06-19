import { Component, OnInit, Input } from '@angular/core';
import {Product} from '../models/product';
import { ProductService } from '../shared/product.service';
import { Type } from '../models/Type';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.css']
})
export class FiltersComponent implements OnInit
{
  @Input() carList: Array<Product>;
  @Input() typeList: Array<Type>;
  fromPrice: number;
  toPrice: number;
  fromPower: number;
  toPower: number;
  selectedOption: string;
  option: string;
  productList: Array<Product>;
  newProductList: Array<Product>;
  orginalList: Array<Product>;

  constructor(private productService: ProductService) {  }

  ngOnInit(): void {
      this.productList = this.carList;
      this.orginalList = this.carList;
      this.newProductList = this.carList;
  }

  onSubmit(selectedOption): void
  {
    this.newProductList = [...this.productList];

    if (this.fromPrice === undefined || this.fromPrice == null) { this.fromPrice = 0; }
    if (this.toPrice === undefined || this.toPrice == null) { this.toPrice = Number.MAX_SAFE_INTEGER; }
    if (this.fromPower === undefined || this.fromPower == null) { this.fromPower = 0; }
    if (this.toPower === undefined || this.toPower == null) { this.toPower = Number.MAX_SAFE_INTEGER; }


    console.log(this.newProductList);

    if (this.selectedOption === undefined || this.selectedOption == null || this.selectedOption === '')
    {
      this.newProductList = this.orginalList.filter( product =>  {return product.price >= this.fromPrice
        && product.price <= this.toPrice
        && this.parseToNumber(product.engineCapacity) >= this.fromPower
        && this.parseToNumber(product.engineCapacity) <= this.toPower });
    }
    else
    {
      this.newProductList = this.orginalList.filter( product => { return product.price >= this.fromPrice
        && product.price <= this.toPrice
        && this.parseToNumber(product.engineCapacity) >= this.fromPower
        && this.parseToNumber(product.engineCapacity) <= this.toPower
        && product.type === this.selectedOption} );
    }

    console.log(this.newProductList);
    this.productList = [...this.newProductList];
    this.newProductList  = [];
  }

  parseToNumber(string: string)
  {
    return parseFloat(string);
  }


}
