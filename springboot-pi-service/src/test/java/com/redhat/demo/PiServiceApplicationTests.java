package com.redhat.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PiServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldGetPiResource() throws Exception {
		mockMvc.perform(get("/pi/5"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.pi").isNumber());
	}

}
