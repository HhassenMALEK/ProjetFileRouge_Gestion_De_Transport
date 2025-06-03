import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarpoolingResaDetailComponent } from './carpooling-resa-detail.component';

describe('CarpoolingResaDetailComponent', () => {
  let component: CarpoolingResaDetailComponent;
  let fixture: ComponentFixture<CarpoolingResaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarpoolingResaDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarpoolingResaDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
