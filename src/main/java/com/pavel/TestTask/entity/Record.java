package com.pavel.TestTask.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pavel.TestTask.entity.auth.User;

@Entity
@Table(name = "record")
public class Record {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank	
	private Float distance;

	@NotBlank	
	private Float time;
	
	@NotBlank
	private Date date;

	@ManyToOne
	@JoinColumn(name = "id_user")
	@JsonIgnore
	private User user;

	
	public Record() {
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public double getDistance() {
		return distance;
	}


	public void setDistance(Float distance) {
		this.distance = distance;
	}


	public double getTime() {
		return time;
	}


	public void setTime(Float time) {
		this.time = time;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	

}