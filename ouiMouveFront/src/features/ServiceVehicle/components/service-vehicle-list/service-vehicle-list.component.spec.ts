import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceVehicleListComponent } from './service-vehicle-list.component';

describe('ServiceVehicleListComponent', () => {
  let component: ServiceVehicleListComponent;
  let fixture: ComponentFixture<ServiceVehicleListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServiceVehicleListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceVehicleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
