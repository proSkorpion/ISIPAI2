import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Type } from '../models/Type';
import { ProductService } from './product.service';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class TypeResolverService  implements Resolve<Observable<Array<Type>>>
{

  constructor(private carService: ProductService) { }

  resolve(route: ActivatedRouteSnapshot)
  {
    return this.carService.getAllTypes();
  }
}
