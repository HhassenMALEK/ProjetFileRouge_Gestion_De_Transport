import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCarpoolingComponent } from './create-carpooling.component';

describe('CreateCarpoolingComponent', () => {
  let component: CreateCarpoolingComponent;
  let fixture: ComponentFixture<CreateCarpoolingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateCarpoolingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateCarpoolingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
