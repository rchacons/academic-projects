import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SupportMemberListComponent } from './support-member-list.component';

describe('SupportMemberListComponent', () => {
  let component: SupportMemberListComponent;
  let fixture: ComponentFixture<SupportMemberListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SupportMemberListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SupportMemberListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
