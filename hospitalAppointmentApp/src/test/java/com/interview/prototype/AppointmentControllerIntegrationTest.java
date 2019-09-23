package com.interview.prototype;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.interview.prototype.model.Appointment;
import com.interview.prototype.model.Patient;
import com.interview.prototype.repositories.AppointmentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = HospitalAppointmentApp.class)
@AutoConfigureMockMvc 
//@AutoConfigureTestDatabase
public class AppointmentControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AppointmentRepository repository;
    
    private final static String CONFIRMATION_ID="APT-JD1001";

    @After
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateAppointment() throws IOException, Exception {
    	LocalDate dob = LocalDate.of(2001, 9, 20);
    	LocalDateTime aptDateTime = LocalDateTime.of(2001, 9, 20 , 10, 0, 0);
    	Patient p = new Patient("John", "Doe", Date.valueOf(dob));
        Appointment apt = new Appointment(1L, CONFIRMATION_ID, Timestamp.valueOf(aptDateTime), p);
        
        mvc.perform(post("/appointment")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content("{\"fname\":\"John\",\"lname\":\"Doe\",\"dateOfBirth\":\"2010-12-12\",\"appointmentDate\":\"2020-12-12T10:00\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.confirmationId", startsWith("APT-JD110")));
    }

    @Test
    public void givenConfirmationId_whenGetAppointment_thenStatus200() throws Exception {
    	createTestAppointment();

        mvc.perform(get("/appointment/"+CONFIRMATION_ID).contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.confirmationId", is(CONFIRMATION_ID)));
    }

    @Test
    public void givenInvalidConfirmationId_whenGetAppointment_thenStatus404() throws Exception {
        mvc.perform(get("/appointment/"+CONFIRMATION_ID+1).contentType(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().isNotFound());
    }
    
    private void createTestAppointment() {
    	LocalDate dob = LocalDate.of(2001, 9, 20);
    	LocalDateTime aptDateTime = LocalDateTime.of(2001, 9, 20 , 10, 0, 0);
    	Patient p = new Patient("John", "Doe", Date.valueOf(dob));
        Appointment apt = new Appointment(1L, CONFIRMATION_ID, Timestamp.valueOf(aptDateTime), p);
        
        repository.save(apt);
    }

}
