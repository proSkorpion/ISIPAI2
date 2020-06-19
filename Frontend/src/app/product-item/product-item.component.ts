import { Component, OnInit, Input } from '@angular/core';
import { Product } from 'src/app/models/product'

import { from } from 'rxjs';

@Component({
  selector: 'app-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.css']
})
export class ProductItemComponent implements OnInit {
  @Input() productItem: Product;
  constructor() { }

  ngOnInit(): void {
  }

  parsePriceToString()
  {
    return this.productItem.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
  }

}
