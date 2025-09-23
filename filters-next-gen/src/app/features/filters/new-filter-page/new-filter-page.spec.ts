import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewFilterPage } from './new-filter-page';

describe('NewFilterPage', () => {
  let component: NewFilterPage;
  let fixture: ComponentFixture<NewFilterPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewFilterPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewFilterPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
