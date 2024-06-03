import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EducationalProgramScreenComponent } from './educational-program-screen.component';

describe('EducationalProgramScreenComponent', () => {
  let component: EducationalProgramScreenComponent;
  let fixture: ComponentFixture<EducationalProgramScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EducationalProgramScreenComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EducationalProgramScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
