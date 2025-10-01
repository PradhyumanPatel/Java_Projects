package com.example.ExpenseTracker.controller;

import com.example.ExpenseTracker.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@SuppressWarnings("removal")
	@MockBean
	ExpenseService sevice;
	
	@Test
	void testList() throws Exception{
		mockMvc.perform(get("/api/expenses")).andExpect(status().isOk());
	}
}
