package com.interview.prototype;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.interview.prototype.model.Appointment;
import com.interview.prototype.model.Patient;
import com.interview.prototype.repositories.AppointmentRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AppointmentRepositoryIntegrationTest {
 
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @After
    public void resetDb() {
    	appointmentRepository.deleteAll();
    }
 
    @Test
    public void whenFindByConfirmationId_thenReturnAppointment() {
        // given
    	LocalDate dob = LocalDate.of(2001, 9, 20);
    	LocalDateTime aptDateTime = LocalDateTime.of(2001, 9, 20 , 10, 0, 0);
    	Patient p = new Patient("John", "Doe", Date.valueOf(dob));
        Appointment apt = new Appointment(100L, "APT-JD1001", Timestamp.valueOf(aptDateTime), p);
        appointmentRepository.save(apt);
     
        // when
        Appointment found = appointmentRepository.findByConfirmationIdIgnoreCase(apt.getConfirmationId()).get();
     
        // then
        assertThat(found.getPatient().getFname()).isEqualTo(apt.getPatient().getFname());
    }
    
    @Test
    public void whenCreateAppointment_thenReturnAppointment() {
        // given
    	LocalDate dob = LocalDate.of(2001, 9, 20);
    	LocalDateTime aptDateTime = LocalDateTime.of(2001, 9, 20 , 10, 0, 0);
    	Patient p = new Patient("John", "Doe", Date.valueOf(dob));
        Appointment apt = new Appointment(10L, "APT-JD1001", Timestamp.valueOf(aptDateTime), p);
        appointmentRepository.save(apt);
        
        // when
        Appointment found = appointmentRepository.findByConfirmationIdIgnoreCase(apt.getConfirmationId()).get();
     
        // then
        assertThat(found.getConfirmationId()).isEqualTo(apt.getConfirmationId());
    }
 
}