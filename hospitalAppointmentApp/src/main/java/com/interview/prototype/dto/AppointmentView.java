package com.interview.prototype.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interview.prototype.model.Appointment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppointmentView {

	private String confirmationId;
	
	@NotNull(message = "appointmentDate is mandatory") 
	@Future(message = "Appointment date must be a future date time") 
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
	private Date appointmentDate;
	
	@NotBlank(message = "fname is mandatory") 
	private String fname;
	
	@NotBlank(message = "lname is mandatory") 
	private String lname;
	
	@NotNull(message = "dateOfBirth is mandatory") 
	@Past(message = "dateOfBirth must be a date in past") 
	@JsonFormat(pattern = "yyyy-MM-dd") 
	private Date dateOfBirth;

	public AppointmentView(@NonNull Appointment appointment) {
		this(appointment.getConfirmationId(), 
				new Date(appointment.getAppointmentDate().getTime()),
					appointment.getPatient().getFname(), appointment.getPatient().getLname(),
						appointment.getPatient().getDateOfBirth());
	}
}
