import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListCarpoolingOrganiserComponent } from './list-carpooling-organiser.component';

describe('ListCarpoolingOrganiserComponent', () => {
  let component: ListCarpoolingOrganiserComponent;
  let fixture: ComponentFixture<ListCarpoolingOrganiserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListCarpoolingOrganiserComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListCarpoolingOrganiserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
