import { Injectable } from '@angular/core';

import { Observable} from 'rxjs';
import { ProductService } from './product.service';
import { Product } from '../models/product';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class DataResolverService implements Resolve<Observable<Array<Product>>> {

  constructor(private carService: ProductService) { }

  resolve(route: ActivatedRouteSnapshot)
  {
    return this.carService.getProduct();
  }

}
