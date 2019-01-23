package com.pavel.TestTask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pavel.TestTask.entity.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

	@Query("SELECT rec FROM Record rec where rec.user.id = :id")
	List<Record> findAllByUserId(@Param("id_user") Long id);
}
