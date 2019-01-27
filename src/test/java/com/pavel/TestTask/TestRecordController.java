package com.pavel.TestTask;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavel.TestTask.controller.AuthController;
import com.pavel.TestTask.entity.Record;
import com.pavel.TestTask.security.JwtResponse;
import com.pavel.TestTask.security.Login;
import com.pavel.TestTask.service.RecordService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/applicationtest.properties")
public class TestRecordController {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AuthController authController;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private RecordService recordService;
	
	private String token;
	
	@Before
	public void auth() {
				
		Login login = new Login();
		login.setUsername("user2");
		login.setPassword("123456");
		JwtResponse response = (JwtResponse) authController.loginUser(login).getBody();

		token = response.toString();
	}
		
	@Test
	public void testGetRecordById() throws Exception {		
				
		Record rec = recordService.getRecordById(5L);
		mockMvc.perform(get("/record/{id}", 2L).header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(rec.getId()))
		.andExpect(jsonPath("$.distance").value(rec.getDistance()))
		.andExpect(jsonPath("$.time").value(rec.getTime()));			   
	}
	
	@Test
	public void testGetAllRecordsByUserId() throws Exception {
		
		mockMvc.perform(get("/records/{id}", 1L).header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
	}

	@Test
	public void testPostRecordByUserId() throws Exception {
				
		Record rec = new Record();
		rec.setDistance(15.3f);
		rec.setDate(new Date(2019,01,28));
		rec.setTime(1.2f);

		mockMvc.perform(post("/record/{id}", 1L).header("Authorization", token)
				.content(objectMapper.writeValueAsString(rec)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isCreated())				
				.andExpect(jsonPath("$.distance").value(rec.getDistance()))
				.andExpect(jsonPath("$.time").value(rec.getTime()));
	}
	
	@Test
	public void testPutRecordByUserId() throws Exception {
				
		Record rec = new Record();
		rec.setDistance(15.4f);
		rec.setDate(new Date(2019,01,28));
		rec.setTime(1.3f);

		mockMvc.perform(put("/record/{id}", 2L).header("Authorization", token)
				.content(objectMapper.writeValueAsString(rec)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteRecordById() throws Exception {
		
		mockMvc.perform(delete("/record/{id}", 3L).header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
	}
	
	@After
	public void clear() {
		List<Record> lst = recordService.getAllRecords();
		for(Record rec : lst)
		{
			recordService.deleteRecord(rec.getId());
		}
	}
	
}
