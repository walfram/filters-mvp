import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterActionsComponent } from './filter-actions.component';

describe('FilterActionsComponent', () => {
  let component: FilterActionsComponent;
  let fixture: ComponentFixture<FilterActionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FilterActionsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FilterActionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
