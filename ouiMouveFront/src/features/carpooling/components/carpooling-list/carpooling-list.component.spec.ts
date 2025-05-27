import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarpoolingListComponent } from './carpooling-list.component';

describe('CarpoolingListComponent', () => {
  let component: CarpoolingListComponent;
  let fixture: ComponentFixture<CarpoolingListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarpoolingListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarpoolingListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
