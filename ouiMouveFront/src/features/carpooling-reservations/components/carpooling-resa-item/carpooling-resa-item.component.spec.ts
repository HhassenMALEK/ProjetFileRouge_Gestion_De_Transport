import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarpoolingResaItemComponent } from './carpooling-resa-item.component';

describe('CarpoolingResaItemComponent', () => {
  let component: CarpoolingResaItemComponent;
  let fixture: ComponentFixture<CarpoolingResaItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarpoolingResaItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarpoolingResaItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
