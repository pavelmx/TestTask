package com.pavel.TestTask.service;

import java.util.List;

import com.pavel.TestTask.entity.Record;

public interface RecordService {

	List<Record> getAllRecords();

	Record getRecordById(Long id);

	List<Record> findAllByUserId(Long id);
	
	Record addRecord(Long id, Record a);

	Record updateRecord(Long id, Record a);

	void deleteRecord(Long id);

	boolean existsRecord(Long id);
}
