import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCarpoolingComponent } from './edit-carpooling.component';

describe('EditCarpoolingComponent', () => {
  let component: EditCarpoolingComponent;
  let fixture: ComponentFixture<EditCarpoolingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditCarpoolingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditCarpoolingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
