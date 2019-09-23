import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';

import { ScheduleAppointmentComponent } from './schedule-appointment.component';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

describe('ScheduleAppointmentComponent', () => {
  let component: ScheduleAppointmentComponent;
  let fixture: ComponentFixture<ScheduleAppointmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScheduleAppointmentComponent ],
      imports: [
        BrowserModule,
        ReactiveFormsModule,
        HttpClientModule
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScheduleAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy(); 
  });

  it(`should have h2 as 'Schedule Appointment'`, async(() => {
    const compile = fixture.debugElement.nativeElement;
    const h2Atag = compile.querySelector('h2');
    expect(h2Atag.textContent).toEqual('Schedule Appointment');
  }));

  
});
