package com.interview.prototype.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String fname;
	private String lname;
	@Temporal(value=TemporalType.DATE)
	private Date dateOfBirth;
	
	public Patient(String fname, String lname, Date dateOfBirth) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.dateOfBirth = dateOfBirth;
	}
}
