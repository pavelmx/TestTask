package com.pavel.TestTask.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class Report {
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date begin_week;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date end_week;
	
	private Float av_speed;
	
	private Float av_time;
	
	private Float total_distance;
		
		
	public Report() {		
	}

	public Report(Date begin_week, Date end_week, Float av_speed, Float av_time, Float total_distance) {
		super();
		this.begin_week = begin_week;
		this.end_week = end_week;
		this.av_speed = av_speed;
		this.av_time = av_time;
		this.total_distance = total_distance;
		
	}

	public Date getBegin_week() {
		return begin_week;
	}

	public void setBegin_week(Date begin_week) {
		this.begin_week = begin_week;
	}

	public Date getEnd_week() {
		return end_week;
	}

	public void setEnd_week(Date end_week) {
		this.end_week = end_week;
	}

	public Float getAv_speed() {
		return av_speed;
	}

	public void setAv_speed(Float av_speed) {
		this.av_speed = av_speed;
	}

	public Float getAv_time() {
		return av_time;
	}

	public void setAv_time(Float av_time) {
		this.av_time = av_time;
	}

	public Float getTotal_distance() {
		return total_distance;
	}

	public void setTotal_distance(Float total_distance) {
		this.total_distance = total_distance;
	}

	
	
}
