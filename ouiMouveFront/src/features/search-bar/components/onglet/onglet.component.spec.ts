import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OngletComponent } from './onglet.component';

describe('OngletComponent', () => {
  let component: OngletComponent;
  let fixture: ComponentFixture<OngletComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OngletComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OngletComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
