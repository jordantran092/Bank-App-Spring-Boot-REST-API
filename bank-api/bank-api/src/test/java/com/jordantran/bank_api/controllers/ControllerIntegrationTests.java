package com.jordantran.bank_api.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jordantran.bank_api.TestDataUtil;
import com.jordantran.bank_api.domain.dto.BankDTO;
import com.jordantran.bank_api.domain.dto.ClientDTO;
import com.jordantran.bank_api.services.BankService;




@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class ControllerIntegrationTests {
	
	private BankService bankService;
	private ObjectMapper objectMapper;
	private MockMvc mockMvc;
	
	@Autowired
	public ControllerIntegrationTests(BankService bankService, ObjectMapper objectMapper, MockMvc mockMvc) {
		this.bankService = bankService;
		this.objectMapper = new ObjectMapper();
		this.mockMvc = mockMvc;
	}
	
	
	
	@Test
	void testThatClientCreationReturnsHttp201() throws Exception {

		
		BankDTO bankDTO = TestDataUtil.createBank();
		ClientDTO clientDTO = TestDataUtil.createClientA(bankDTO);
		
		
		String clientJson = objectMapper.writeValueAsString(clientDTO);
	
		
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        ).andExpect(
        		// check status of http response result for 201 created
                MockMvcResultMatchers.status().isCreated()
        );
	}
	
	@Test
	void testThatClientCreationReturnsSavedClient() throws Exception {

		
		BankDTO bankDTO = TestDataUtil.createBank();
		ClientDTO clientDTO = TestDataUtil.createClientA(bankDTO);
		
		
		String clientJson = objectMapper.writeValueAsString(clientDTO);
	
		
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber() // auto generated, so as long as number, good
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("John")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.balance").value(20.3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.numOfTransactions").value(0)
        ).andExpect(
        		// bankDTO is a nested object, requires dot navigation
                MockMvcResultMatchers.jsonPath("$.bankDTO.error").value(false)
		).andExpect(
                MockMvcResultMatchers.jsonPath("$.bankDTO.errorStr").value("")
        );
	}
	
	

	
	

}
