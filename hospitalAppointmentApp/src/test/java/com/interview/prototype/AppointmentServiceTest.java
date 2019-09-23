package com.interview.prototype;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.interview.prototype.model.Appointment;
import com.interview.prototype.model.Patient;
import com.interview.prototype.repositories.AppointmentRepository;
import com.interview.prototype.services.AppointmentService;

@RunWith(SpringRunner.class)
public class AppointmentServiceTest {

	@TestConfiguration
    static class AppointmentServiceTestContextConfiguration {
  
        @Bean
        public AppointmentService appointmentService() {
            return new AppointmentService();
        }
    }
	
	@Autowired
    private AppointmentService appointmentService;
 
    @MockBean
    private AppointmentRepository appointmentRepository;
    
    private final static String CONFIRMATION_ID="APT-JD1001";
    
    @Before
    public void setUp() {
    	// given
    	LocalDate dob = LocalDate.of(2001, 9, 20);
    	LocalDateTime aptDateTime = LocalDateTime.of(2001, 9, 20 , 10, 0, 0);
    	Patient p = new Patient("John", "Doe", Date.valueOf(dob));
        Optional<Appointment> apt = Optional.ofNullable(new Appointment(1L, CONFIRMATION_ID, Timestamp.valueOf(aptDateTime), p));
     
        Mockito.when(appointmentRepository.findByConfirmationIdIgnoreCase(CONFIRMATION_ID))
          .thenReturn(apt);
    }
    
    @Test
    public void whenValidConfirmationId_thenAppointmentShouldBeFound() {
    	//when
        Appointment found = appointmentService.retrieve(CONFIRMATION_ID).get();
      
        //then
        assertThat(found.getConfirmationId()).isEqualTo(CONFIRMATION_ID);
     }
}
