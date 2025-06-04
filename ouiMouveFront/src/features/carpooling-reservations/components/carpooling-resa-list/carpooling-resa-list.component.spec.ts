import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarpoolingResaListComponent } from './carpooling-resa-list.component';

describe('CarpoolingResaListComponent', () => {
  let component: CarpoolingResaListComponent;
  let fixture: ComponentFixture<CarpoolingResaListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarpoolingResaListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarpoolingResaListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
