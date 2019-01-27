package com.pavel.TestTask;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.pavel.TestTask.repository.UserRepository;
import com.pavel.TestTask.security.Login;
import com.pavel.TestTask.security.Register;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/applicationtest.properties")
public class TestAuthController {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Test
	public void testLogin() throws Exception {
		Login login = new Login();
		login.setUsername("user2");
		login.setPassword("123456");

		mockMvc.perform(post("/auth/login").content(objectMapper.writeValueAsString(login))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.tokenType").value("Bearer"))
				.andExpect(jsonPath("$.accessToken").isNotEmpty());
	}
	
	@Test
	public void testRegister() throws Exception {
		Register reg = new Register();
		reg.setUsername("user3");
		reg.setPassword("123456");
		reg.setEmail("user3@email");
		reg.setName("ivan ivanov");

		mockMvc.perform(post("/auth/register").content(objectMapper.writeValueAsString(reg))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());		
	}
}

