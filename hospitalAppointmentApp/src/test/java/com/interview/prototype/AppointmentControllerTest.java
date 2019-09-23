package com.interview.prototype;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.interview.prototype.controllers.AppointmentController;
import com.interview.prototype.dto.AppointmentView;
import com.interview.prototype.exceptions.ResourceNotFoundException;
import com.interview.prototype.model.Appointment;
import com.interview.prototype.model.Patient;
import com.interview.prototype.services.AppointmentService;
import com.interview.prototype.util.JsonUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(value=AppointmentController.class)
public class AppointmentControllerTest {
	
	@Autowired
    private MockMvc mvc;
 
    @MockBean
    private AppointmentService appointmentService;
    
    private final static String CONFIRMATION_ID="APT-JD1001";

    @Test
    public void givenConfirmationId_whenGetAppointment_thenReturnJsonAppointmetView()
      throws Exception {
         
    	// given
    	LocalDate dob = LocalDate.of(2001, 9, 20);
    	LocalDateTime aptDateTime = LocalDateTime.of(2001, 9, 20 , 10, 0, 0);
    	Patient p = new Patient("John", "Doe", Date.valueOf(dob));
        Appointment apt = new Appointment(1L, CONFIRMATION_ID, Timestamp.valueOf(aptDateTime), p);
     
        given(appointmentService.retrieve(Mockito.any())).willReturn(Optional.of(apt));
     
        mvc.perform(get("/appointment/"+CONFIRMATION_ID)
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.confirmationId", is(apt.getConfirmationId())));
        
        verify(appointmentService, times(1)).retrieve(Mockito.any());
    }
    
    @Test
    public void givenAppointmentView_whenCreateAppointment_thenReturnJsonAppointmetView()
      throws Exception {
         
    	LocalDate dob = LocalDate.of(2001, 9, 20);
    	LocalDateTime aptDateTime = LocalDateTime.of(2001, 9, 20 , 10, 0, 0);
    	Patient p = new Patient("John", "Doe", Date.valueOf(dob));
        Appointment apt = new Appointment(1L, CONFIRMATION_ID, Timestamp.valueOf(aptDateTime), p);
     
        given(appointmentService.create(Mockito.any())).willReturn(Optional.of(apt));
        
        mvc.perform(post("/appointment")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content("{\"fname\":\"John\",\"lname\":\"Doe\",\"dateOfBirth\":\"2010-12-12\",\"appointmentDate\":\"2020-12-12T10:00\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.confirmationId", is(CONFIRMATION_ID)));
        
        verify(appointmentService, times(1)).create(Mockito.any());
    }
    
    @Test
    public void givenInvalidConfirmationId_whenGetAppointment_thenThrowResourceNotFoundException() throws Exception {
    	
    	given(appointmentService.retrieve(Mockito.any())).willReturn(Optional.ofNullable(null));
    	
    	 mvc.perform(get("/appointment/APT-JD100112")
    	          .contentType(MediaType.APPLICATION_JSON))
    	          .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void givenInvalidConfirmationId_whenGetAppointment_thenThrowConstraintViolationException() throws Exception {
        mvc.perform(post("/appointment").contentType(MediaType.APPLICATION_JSON).content("{\"\":\"John\",\"lname\":\"Doe\",\"dateOfBirth\":\"2010-12-12\",\"appointmentDate\":\"2020-12-12T10:00\"}"))
        .andExpect(status().isBadRequest());
    }
}
