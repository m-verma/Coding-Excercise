package com.interview.prototype.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.interview.prototype.dto.AppointmentView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String confirmationId;
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date appointmentDate;
	@OneToOne(cascade = CascadeType.ALL)
	private Patient patient;
	
	public Appointment(AppointmentView appointmentView) {
		super();
		this.confirmationId = generateConfirmationId(appointmentView);
		this.appointmentDate = appointmentView.getAppointmentDate();
		Patient p = new Patient(appointmentView.getFname(), appointmentView.getLname(), appointmentView.getDateOfBirth());
		this.patient = p;
	}

	private String generateConfirmationId(AppointmentView appointmentView) {
		return "APT-" + appointmentView.getFname().charAt(0) + appointmentView.getLname().charAt(0) + appointmentView.getDateOfBirth().getYear() + ((int) (Math.random()*100)+1);
	}
}
