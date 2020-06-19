import { Injectable } from '@angular/core';
import { Product} from '../models/product';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Type } from '../models/Type';

@Injectable({
  providedIn: 'root'
})
export class ProductService
{
  constructor(private http: HttpClient) { }



  private BASE_URL = 'https://localhost:443';
  private GET_ALL_CARS = `${this.BASE_URL}/car/get/all`;
  private GET_ALL_TYPES = `${this.BASE_URL}/type/get/all`;

  getProduct(): Observable<Array<Product>>
  {
    return this.http.get<Array<Product>>(this.GET_ALL_CARS);
  }

  getAllTypes(): Observable<Array<Type>>
  {
    return this.http.get<Array<Type>>(this.GET_ALL_TYPES);
  }


}
