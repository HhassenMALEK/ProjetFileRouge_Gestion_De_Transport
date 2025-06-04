import { Configuration } from './configuration';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CustomConfiguration extends Configuration {
  constructor() {
    super();
  }
  
  public override selectHeaderAccept(accepts: string[]): string {
    return 'application/json';
  }
}