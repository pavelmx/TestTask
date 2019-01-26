package com.pavel.TestTask.service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pavel.TestTask.entity.Record;
import com.pavel.TestTask.entity.Report;
import com.pavel.TestTask.entity.auth.User;
import com.pavel.TestTask.repository.RecordRepository;
import com.pavel.TestTask.repository.UserRepository;

@Service
public class RecordServiseImpl implements RecordService{

	@Autowired
	RecordRepository recordRepo;

	@Autowired
	UserRepository userRepo;
	
	

	@Override
	public List<Record> getAllRecords() {
		return recordRepo.findAll();
	}

	@Override
	public Record getRecordById(Long id) {
		if (!existsRecord(id)) {
			throw new EntityNotFoundException("not found record with this id");
		}
		return recordRepo.findById(id).get();
	}

	@Override
	public List<Record> findAllByUserId(Long id) {
		if (!userRepo.existsById(id)) {
			throw new EntityNotFoundException("not found user with this id");
		}
		return recordRepo.findAllByUserId(id);
	}

	@Override
	public Record addRecord(Long id, Record r) {
		if (!userRepo.existsById(id)) {
			throw new EntityExistsException("not found user with this id");
		}
		User u = userRepo.findById(id).get();
		r.setUser(u);
		recordRepo.save(r);
		return r;
	}

	@Override
	public Record updateRecord(Long id, Record r) {
		if (!userRepo.existsById(id)) {
			throw new EntityNotFoundException("not found user with this id");
		}
		User u = userRepo.findById(id).get();
		r.setUser(u);
		recordRepo.save(r);
		return r;
	}

	@Override
	public void deleteRecord(Long id) {		
		recordRepo.deleteById(id);
	}

	@Override
	public boolean existsRecord(Long id) {
		return recordRepo.existsById(id);
	}
	
	@Override
	public List<Report> getReport(Long id) {		
		return recordRepo.getReport(id);			
	}

}
