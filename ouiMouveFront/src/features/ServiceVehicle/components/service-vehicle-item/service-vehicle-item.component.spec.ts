import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceVehicleItemComponent } from './service-vehicle-item.component';

describe('ServiceVehicleItemComponent', () => {
  let component: ServiceVehicleItemComponent;
  let fixture: ComponentFixture<ServiceVehicleItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServiceVehicleItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceVehicleItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
