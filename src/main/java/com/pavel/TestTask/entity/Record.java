package com.pavel.TestTask.entity;

import java.util.Date;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pavel.TestTask.entity.auth.User;


@SqlResultSetMapping(name = "Report", classes = {
		@ConstructorResult(targetClass = Report.class, columns = {
				@ColumnResult(name = "begin_week", type = Date.class),
				@ColumnResult(name = "end_week", type = Date.class),
				@ColumnResult(name = "av_speed", type = Float.class),
				@ColumnResult(name = "av_time", type = Float.class),
				@ColumnResult(name = "total_distance", type = Float.class) }) })

@Entity
@NamedNativeQuery(name="Record.getReport",query="SELECT DATE_SUB(date, (DAYOFWEEK(date)-2) day ) AS begin_week, " + 
					"DATE_ADD(date, (8-DAYOFWEEK(date)) day ) AS end_week, " + 
					"SUM(distance/time)/COUNT(*) AS av_speed, " + 
					"SUM(time)/COUNT(time) AS av_time, " + 
					"SUM(distance) AS total_distance" + 
					" FROM myrecord WHERE id_user = :id GROUP BY WEEK(DATE_ADD(date, 1 DAY)), begin_week, end_week",
					resultSetMapping = "Report",
					resultClass=Report.class)
@Table(name = "myrecord")
public class Record {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	private Float distance;

		
	private Float time;
	
	
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