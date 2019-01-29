package com.pavel.TestTask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pavel.TestTask.entity.Record;
import com.pavel.TestTask.entity.Report;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

	@Query("SELECT rec FROM Record rec WHERE rec.user.id = :id")
	List<Record> findAllByUserId(@Param("id") Long id);
	
	List<Report> getReport(@Param("id") Long id);
}
