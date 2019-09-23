import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

export class Appointment {
  constructor(
    public fname : String,
    public lname : String,
    public dateOfBirth: Date, 
    public appointmentDate: Date,
    public confirmationId? : String
  ) { }
}

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private httpClient: HttpClient) { }

  getAppointment$(confirmationId : String) {
    return this.httpClient.get<Appointment>(environment.backendBaseUri + "/" + confirmationId);
  }

  public createAppointment$(appointment:Appointment) {
    return this.httpClient.post<Appointment>(environment.backendBaseUri, appointment);
  }
}