package com.interview.prototype.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interview.prototype.model.Appointment;
import com.interview.prototype.repositories.AppointmentRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	public Optional<Appointment> retrieve(String confirmationId) {
		return appointmentRepository.findByConfirmationIdIgnoreCase(confirmationId);
	}

	public Optional<Appointment> create(Appointment appointment) {
		return Optional.ofNullable(appointmentRepository.save(appointment));
	}
}