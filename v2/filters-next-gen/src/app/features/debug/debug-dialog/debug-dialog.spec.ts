import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DebugDialog } from './debug-dialog';

describe('DebugDialog', () => {
  let component: DebugDialog;
  let fixture: ComponentFixture<DebugDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DebugDialog]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DebugDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
