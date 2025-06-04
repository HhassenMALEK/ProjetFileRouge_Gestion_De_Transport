import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarpoolingFormComponent } from './carpooling-form.component';

describe('CarpoolingFormComponent', () => {
  let component: CarpoolingFormComponent;
  let fixture: ComponentFixture<CarpoolingFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarpoolingFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarpoolingFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
