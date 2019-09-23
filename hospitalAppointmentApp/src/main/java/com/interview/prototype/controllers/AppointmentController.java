package com.interview.prototype.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.prototype.dto.AppointmentView;
import com.interview.prototype.exceptions.ApplicationException;
import com.interview.prototype.exceptions.ResourceNotFoundException;
import com.interview.prototype.model.Appointment;
import com.interview.prototype.services.AppointmentService;

@RestController
@Validated
@RequestMapping({ "/appointment" })
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("/{confirmationId}")
	public AppointmentView retrieve(@PathVariable @Size(min=10, max=11, message = "Confirmation Id must be between 10 to 12 characters") String confirmationId) {
		return new AppointmentView(appointmentService.retrieve(confirmationId)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment Not Found")));
	}

	@PostMapping
	public AppointmentView create(@Valid @RequestBody AppointmentView appointmentView, HttpServletResponse response) {
		Appointment appointment = new Appointment(appointmentView);
		Optional<Appointment> persistedAppointment = appointmentService.create(appointment);
		
		response.setStatus(HttpServletResponse.SC_CREATED);
		return new AppointmentView(persistedAppointment.orElseThrow(() -> new ApplicationException("Appointment could not be saved")));
	}
}