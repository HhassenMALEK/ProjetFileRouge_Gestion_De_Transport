import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchResaComponent } from './search-resa.component';

describe('SearchResaComponent', () => {
  let component: SearchResaComponent;
  let fixture: ComponentFixture<SearchResaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SearchResaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchResaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
