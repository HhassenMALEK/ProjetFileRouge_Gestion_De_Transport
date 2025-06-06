import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceVehicleMainComponent } from './service-vehicle-main.component';

describe('ServiceVehicleMainComponent', () => {
  let component: ServiceVehicleMainComponent;
  let fixture: ComponentFixture<ServiceVehicleMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServiceVehicleMainComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceVehicleMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
