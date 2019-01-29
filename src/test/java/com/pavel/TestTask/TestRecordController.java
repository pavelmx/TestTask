package com.pavel.TestTask;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavel.TestTask.controller.AuthController;
import com.pavel.TestTask.entity.Record;
import com.pavel.TestTask.repository.RecordRepository;
import com.pavel.TestTask.repository.UserRepository;
import com.pavel.TestTask.security.JwtResponse;
import com.pavel.TestTask.security.Login;
import com.pavel.TestTask.security.Register;
import com.pavel.TestTask.service.RecordService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/applicationtest.properties")
@Sql(value = { "/after.sql" }, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class TestRecordController {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AuthController authController;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private RecordService recordService;
	
	@Autowired
	private RecordRepository recordRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private String token;
	
	private Long id;
	
	@Before
	public void auth() {
		Register reg = new Register();
		reg.setUsername("user2");
		reg.setPassword("123456");
		reg.setEmail("user@email");
		reg.setName("petya");
		authController.registerUser(reg);
		id = userRepository.findByUsername(reg.getUsername()).get().getId();	
		System.out.println("start "+id);
		Login login = new Login();
		login.setUsername("user2");
		login.setPassword("123456");
		JwtResponse response = (JwtResponse) authController.loginUser(login).getBody();
		token = response.toString();		
	}
		
	@Test
	public void testGetRecordById() throws Exception {		
		Record rec = new Record();		
		rec.setDistance(10f);
		rec.setDate(new Date(2019,01,29));
		rec.setTime(1.5f);		
		Record recnew = recordService.addRecord(id, rec);
		System.out.println("getbyid " + id);
		mockMvc.perform(get("/record/{id}", recnew.getId()).header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(recnew.getId()))
		.andExpect(jsonPath("$.distance").value(recnew.getDistance()))
		.andExpect(jsonPath("$.time").value(recnew.getTime()));			   
	}
	
	@Test
	public void testGetAllRecordsByUserId() throws Exception {
		System.out.println("testGetAllRecordsByUserId "+id);
		mockMvc.perform(get("/records/{id}", id).header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
	}

	@Test
	public void testPostRecordByUserId() throws Exception {
				
		Record rec = new Record();
		rec.setDistance(15.3f);
		rec.setDate(new Date(2019,01,28));
		rec.setTime(1.2f);
		System.out.println("testPostRecordByUserId "+id);
		mockMvc.perform(post("/record/{id}", id).header("Authorization", token)
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
		System.out.println("testPutRecordByUserId "+id);
		mockMvc.perform(put("/record/{id}", id).header("Authorization", token)
				.content(objectMapper.writeValueAsString(rec)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteRecordById() throws Exception {
		Record rec = new Record();		
		rec.setDistance(10f);
		rec.setDate(new Date(2019,01,29));
		rec.setTime(1.5f);		
		System.out.println("testDeleteRecordById "+id);
		Record recnew = recordService.addRecord(id, rec);		
		mockMvc.perform(delete("/record/{id}", recnew.getId()).header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
	}
	
	
}
