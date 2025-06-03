import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateVehicleServiceComponent } from './create-vehicle-service.component';

describe('CreateVehicleServiceComponent', () => {
  let component: CreateVehicleServiceComponent;
  let fixture: ComponentFixture<CreateVehicleServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateVehicleServiceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateVehicleServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
