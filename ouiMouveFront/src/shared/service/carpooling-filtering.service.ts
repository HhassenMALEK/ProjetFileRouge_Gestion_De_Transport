import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CarPoolingResponseDto } from '@openapi/index';

@Injectable({
  providedIn: 'root',
})
export class CarPoolingFilteringService {
  private carPoolings = new BehaviorSubject<CarPoolingResponseDto[]>([]);
  currentCarPoolings = this.carPoolings.asObservable();

  sendCarPoolings(newCarPoolings: CarPoolingResponseDto[]) {
    this.carPoolings.next(newCarPoolings);
  }
}
