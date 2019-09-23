import { Component, OnInit, ViewChild } from '@angular/core';
import { AppointmentService, Appointment } from '../appointment-service/appointment.service';
import { NgForm } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'search-appointment',
  templateUrl: './search-appointment.component.html',
  styleUrls: ['./search-appointment.component.css']
})
export class SearchAppointmentComponent implements OnInit {

  @ViewChild('f') form: NgForm;
  appointment: Appointment;
  confirmationId : String;
  displayError: boolean;
  errors:String[];
  searchSub: Subscription;

  constructor(private appointmentService: AppointmentService) { }

  ngOnInit() {
  }

  getAppointment(): void {
    this.displayError = false;
    this.errors=[];
    this.appointment = null;
    this.searchSub = this.appointmentService.getAppointment$(this.confirmationId).subscribe(
      response => this.appointment = response,
      errorResponse=> {
        //console.error(errorResponse);
        this.displayError = true;
        this.errors=errorResponse.error.messages;
      }
    );
    this.form.resetForm();
  }

  ngOnDestroy() {
    if (this.searchSub) {
      this.searchSub.unsubscribe();
    }
  }
}