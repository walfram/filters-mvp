import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TitleControls } from './title-controls';

describe('TitleControls', () => {
  let component: TitleControls;
  let fixture: ComponentFixture<TitleControls>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TitleControls]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TitleControls);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
