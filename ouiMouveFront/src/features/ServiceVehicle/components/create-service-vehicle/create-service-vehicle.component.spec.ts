import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateServiceVehicleComponent } from './create-service-vehicle.component';

describe('CreateServiceVehicleComponent', () => {
  let component: CreateServiceVehicleComponent;
  let fixture: ComponentFixture<CreateServiceVehicleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateServiceVehicleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateServiceVehicleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
