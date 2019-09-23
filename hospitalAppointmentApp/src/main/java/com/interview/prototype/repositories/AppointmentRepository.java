package com.interview.prototype.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.interview.prototype.model.Appointment;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

	Optional<Appointment> findByConfirmationIdIgnoreCase(String confirmationId);
	
}
