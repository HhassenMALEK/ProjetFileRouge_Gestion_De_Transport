import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceVehicleSearchComponent } from './service-vehicle-search.component';

describe('ServiceVehicleSearchComponent', () => {
  let component: ServiceVehicleSearchComponent;
  let fixture: ComponentFixture<ServiceVehicleSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServiceVehicleSearchComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceVehicleSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
