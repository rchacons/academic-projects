import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SupportMemberComponent } from './support-member.component';

describe('SupportMemberComponent', () => {
  let component: SupportMemberComponent;
  let fixture: ComponentFixture<SupportMemberComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SupportMemberComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SupportMemberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
