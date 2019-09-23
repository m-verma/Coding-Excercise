import { Component, OnInit } from '@angular/core';
import { AppointmentService, Appointment } from '../appointment-service/appointment.service';
import { FormGroup, Validators, FormBuilder, AbstractControl } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'schedule-appointment',
  templateUrl: './schedule-appointment.component.html',
  styleUrls: ['./schedule-appointment.component.css']
})
export class ScheduleAppointmentComponent implements OnInit {

  appointment: Appointment;
  displayError: boolean;
  errors:String[];
  confirmationId:String;

  appointmentForm: FormGroup;

  submitSub: Subscription;

  constructor(private appointmentService: AppointmentService, private fb: FormBuilder) { }

  ngOnInit() {
    this.appointmentForm = this.fb.group({
      'fname': [null, Validators.required],
      'lname': [null, Validators.required],
      'dob': [null, [Validators.required, this.dateInPast]],
      'aptDate': [null, [Validators.required, this.dateInFuture]]
    });
  }

  
  createAppointment(): void {
    this.displayError = false;
    this.errors=[];
    this.confirmationId=null;
    console.log(this.appointmentForm.get('dob'));
    this.appointment = new Appointment( this.appointmentForm.get('fname').value,
                                        this.appointmentForm.get('lname').value, 
                                        this.appointmentForm.get('dob').value, 
                                        this.appointmentForm.get('aptDate').value);
    this.submitSub = this.appointmentService.createAppointment$(this.appointment)
    .subscribe(data => {
      this.confirmationId=data.confirmationId;
    },
    errResponse=>{
      //console.error(errorResponse);
      this.displayError = true;
      this.errors=errResponse.error.messages;
    });

    this.appointmentForm.reset();
  };
  
  dateInPast(control: AbstractControl):{[key: string] : boolean} {
    let now = new Date();
    let controlValue = new Date(control.value);
    if(controlValue>now){
      return {'dateInPast':true};
    }
    return null;
  }
  
  dateInFuture(control: AbstractControl):{[s: string] : boolean} {
    let now = new Date();
    let controlValue = new Date(control.value);
    if(controlValue<now){
      return {'dateInFuture':true};
    }
    return null;
  }

  ngOnDestroy() {
    if (this.submitSub) {
      this.submitSub.unsubscribe();
    }
  }
}