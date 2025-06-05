import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DateAndCityComponent } from './date-and-city.component';

describe('DateAndCityComponent', () => {
  let component: DateAndCityComponent;
  let fixture: ComponentFixture<DateAndCityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DateAndCityComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DateAndCityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
