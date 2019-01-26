package com.pavel.TestTask.service;

import java.util.List;

import com.pavel.TestTask.entity.Record;
import com.pavel.TestTask.entity.Report;

public interface RecordService {

	List<Record> getAllRecords();

	Record getRecordById(Long id);

	List<Record> findAllByUserId(Long id);
	
	Record addRecord(Long id, Record r);

	Record updateRecord(Long id, Record r);

	void deleteRecord(Long id);

	boolean existsRecord(Long id);
	
	List<Report> getReport(Long id);
}
