import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AmountControls } from './amount-controls';

describe('AmountControls', () => {
  let component: AmountControls;
  let fixture: ComponentFixture<AmountControls>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AmountControls]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AmountControls);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
