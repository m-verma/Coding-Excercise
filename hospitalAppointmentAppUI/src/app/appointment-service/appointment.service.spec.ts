import { TestBed, async } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { AppointmentService, Appointment } from './appointment.service';

describe('AppointmentService', () => {

  let httpTestingController: HttpTestingController;
  let service: AppointmentService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    httpTestingController = TestBed.get(HttpTestingController);
    service = TestBed.get(AppointmentService);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it(`'AppointmentService' should be created`, () => {
    //const service: AppointmentService = TestBed.get(AppointmentService);
    expect(service).toBeTruthy();
  });

  describe('createAppointment()', () => {
    it('create appointment should match the right data', () => {
      const mockAppointment : Appointment = {"fname":"John","lname":"Doe","dateOfBirth":new Date('2010-12-12'),"appointmentDate":new Date('2020-12-12T10:00')};
  
      service.createAppointment$(mockAppointment)
        .subscribe(data => {
          expect(data.fname).toEqual('John');
          expect(data.lname).toEqual('Doe');
        });
  
      const req = httpTestingController.expectOne('http://localhost:9090/appointment');
  
      expect(req.request.method).toEqual('POST');
  
      req.flush(mockAppointment);
    });

    it('search appointment should match the right data', () => {
      const mockAppointment : Appointment = {"fname":"John","lname":"Doe","dateOfBirth":new Date('2010-12-12'),"appointmentDate":new Date('2020-12-12T10:00')};
  
      service.getAppointment$("TEST")
        .subscribe(data => {
          expect(data.fname).toEqual('John');
          expect(data.lname).toEqual('Doe');
        });
  
      const req = httpTestingController.expectOne('http://localhost:9090/appointment/TEST');
  
      expect(req.request.method).toEqual('GET');
  
      req.flush(mockAppointment);
    });
  });

});


 