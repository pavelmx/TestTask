package com.pavel.TestTask.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pavel.TestTask.entity.Record;
import com.pavel.TestTask.entity.Report;
import com.pavel.TestTask.service.RecordService;


@RestController
@CrossOrigin(origins = "*")
public class RecordController {

	@Autowired
	RecordService recordService;
	
	@GetMapping("report/{id}")
	public ResponseEntity<List<Report>> getReportByUserId(@PathVariable("id") Long id) {
		return new ResponseEntity<List<Report>>(recordService.getReport(id), HttpStatus.OK);
	}
	
	@GetMapping("record/{id}")
	public ResponseEntity<Record> getRecordById(@PathVariable("id") Long id) {
		Record r = recordService.getRecordById(id);
		return new ResponseEntity<Record>(r, HttpStatus.OK);
	}

	@GetMapping("records")
	public ResponseEntity<List<Record>> getAllRecords() {
		List<Record> list = recordService.getAllRecords();
		return new ResponseEntity<List<Record>>(list, HttpStatus.OK);
	}

	@GetMapping("records/{id}")
	public ResponseEntity<List<Record>> getAllByUserId(@PathVariable("id") Long id) {
		List<Record> list = recordService.findAllByUserId(id);
		return new ResponseEntity<List<Record>>(list, HttpStatus.OK);
	}
	
	@PostMapping("record/{id}")
	public ResponseEntity<Record> addRecordByUserId(@PathVariable("id") Long id, @RequestBody Record r) {
		Record rec = recordService.addRecord(id, r);
		return new ResponseEntity<Record>(rec, HttpStatus.CREATED);
	}

	@PutMapping("record/{id}")
	public ResponseEntity<Record> updateRecordByUserId(@PathVariable("id") Long id, @RequestBody Record r) {
		Record rec = recordService.updateRecord(id, r);
		return new ResponseEntity<Record>(rec, HttpStatus.OK);
	}

	@DeleteMapping("record/{id}")
	public ResponseEntity<Void> deleteRecordById(@PathVariable("id") Long id) {
		recordService.deleteRecord(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
