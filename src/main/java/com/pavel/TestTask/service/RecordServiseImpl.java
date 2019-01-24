package com.pavel.TestTask.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pavel.TestTask.entity.Record;
import com.pavel.TestTask.entity.auth.User;
import com.pavel.TestTask.repository.RecordRepository;
import com.pavel.TestTask.repository.UserRepository;

@Service
public class RecordServiseImpl implements RecordService{

	@Autowired
	RecordRepository recordRepo;

	@Autowired
	UserRepository userRepo;

	public List<Record> getAllRecords() {
		return recordRepo.findAll();
	}

	public Record getRecordById(Long id) {
		if (!existsRecord(id)) {
			throw new EntityNotFoundException("not found record with this id");
		}
		return recordRepo.findById(id).get();
	}

	public List<Record> findAllByUserId(Long id) {
		return recordRepo.findAllByUserId(id);
	}

	public Record addRecord(Long id, Record r) {
		if (!userRepo.existsById(id)) {
			throw new EntityExistsException("not found user with this id");
		}
		User u = userRepo.findById(id).get();
		r.setUser(u);
		recordRepo.save(r);
		return r;
	}

	public Record updateRecord(Long id, Record r) {
		if (!userRepo.existsById(id)) {
			throw new EntityNotFoundException("not found user with this id");
		}
		User u = userRepo.findById(id).get();
		r.setUser(u);
		recordRepo.save(r);
		return r;
	}

	public void deleteRecord(Long id) {		
		recordRepo.deleteById(id);
	}

	public boolean existsRecord(Long id) {
		return recordRepo.existsById(id);
	}

}
