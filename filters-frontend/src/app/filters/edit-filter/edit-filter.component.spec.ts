import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditFilterComponent } from './edit-filter.component';

describe('EditFilterComponent', () => {
  let component: EditFilterComponent;
  let fixture: ComponentFixture<EditFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditFilterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
