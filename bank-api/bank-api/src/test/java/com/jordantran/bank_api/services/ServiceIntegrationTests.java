package com.jordantran.bank_api.services;

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
import com.jordantran.bank_api.domain.dto.*;




@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class ServiceIntegrationTests {
	
	private ObjectMapper objectMapper;
	private MockMvc mockMvc;
	
	
	@Autowired
	public ServiceIntegrationTests(ObjectMapper objectMapper, MockMvc mockMvc) {
		this.objectMapper = new ObjectMapper();
		this.mockMvc = mockMvc;
	}
	
	
	//----------------------------------
	
	
	
	
    /* Bank Service Integration Tests */
    
	@Test
	void test_03a() throws Exception {
		
		
		
        // Get bank status
		/* status of an empty bank */
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Accounts: {}")
        );
        
        
        /* Print statement from a non-existing account. */
        
        // Get statement
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/clients/" + "Heeyeon")
        ).andExpect(
        		MockMvcResultMatchers.status().isNotFound()
        );

        // Get bank status
        
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Error: From-Account Heeyeon does not exist")
        );
        
        
        
        
        /* deposit to a non-existing account */
        
		TransactionDepositWithdrawDTO transactionDTO = TransactionDepositWithdrawDTO.builder()
				.transactionType("DEPOSIT")
				.amount(300.05)
				.name("Heeyeon")
				.build();
	
		String transactionJson = objectMapper.writeValueAsString(transactionDTO);
        
        // Deposit
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + transactionDTO.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        );
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Error: To-Account Heeyeon does not exist")
        );
        
        
        transactionDTO.setAmount(-300.05);
        transactionDTO.setName("Jiyoon");
        transactionJson = objectMapper.writeValueAsString(transactionDTO);
        
        // Deposit        
        
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + transactionDTO.getName()) /* error of non-existing to-account takes priority (see error tables in PDF instructions) */
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        );
        
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Error: To-Account Jiyoon does not exist")
        );
        
        
        
        /* withdraw from a non-existing account */
        
        transactionDTO.setTransactionType("WITHDRAW");
        transactionDTO.setAmount(10.5);
        transactionDTO.setName("Heeyeon");
        transactionJson = objectMapper.writeValueAsString(transactionDTO);
        
        // Withdraw     
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + transactionDTO.getName()) /* error of non-existing to-account takes priority (see error tables in PDF instructions) */
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        );
       
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Error: From-Account Heeyeon does not exist")
        );
        
        
        transactionDTO.setTransactionType("WITHDRAW");
        transactionDTO.setAmount(-300.05);
        transactionDTO.setName("Jiyoon");
        transactionJson = objectMapper.writeValueAsString(transactionDTO);
        
        
        // Withdraw     
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + transactionDTO.getName()) /* error of non-existing to-account takes priority */
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        );
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Error: From-Account Jiyoon does not exist")
        );
        

        
        
        
        /* transfer between two non-existing accounts: error of from-account takes priority */
        
		TransactionTransferDTO transactionTransferDTO = TransactionTransferDTO.builder()
					.amount(100.24)
					.fromName("Heeyeon")
					.toName("Jiyoon")
					.build();
		
		String transactionTransferJson = objectMapper.writeValueAsString(transactionTransferDTO);
	
		// Transfer
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionTransferJson)
        );
        
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Error: From-Account Heeyeon does not exist")
        );
        
        
        
      
        transactionTransferDTO.setAmount(-100.24);
        transactionTransferDTO.setFromName("Jiyoon");
        transactionTransferDTO.setToName("Heeyeon");
        transactionTransferJson = objectMapper.writeValueAsString(transactionTransferDTO);
        
		// Transfer
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionTransferJson)
        );
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Error: From-Account Jiyoon does not exist")
        );
        


    
	}
	
	
	@Test
	void test_03b() throws Exception {
		
		

		ClientDTO clientDTO = TestDataUtil.createClientB_DTO();
		clientDTO.setName("Heeyeon");
		clientDTO.setBalance(-23.5);
		String clientJson = objectMapper.writeValueAsString(clientDTO);
	
		// Add Client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Error: Non-Positive Initial Balance")
        );
        
        
		clientDTO.setName("Heeyeon");
		clientDTO.setBalance(0);
		clientJson = objectMapper.writeValueAsString(clientDTO);
	
		// Add Client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
        
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Error: Non-Positive Initial Balance")
        );
        
        // --------------------------
		
        
		clientDTO.setName("Heeyeon");
		clientDTO.setBalance(213.4);
		clientJson = objectMapper.writeValueAsString(clientDTO);
	
		// Add Client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Accounts: {Heeyeon: $213.40}")
        );
        
        
        /*
         * A client's statement summarizes
         * their current status followed by their history list of transactions.
         */
        
        
        // Get statement
        /* The added account Heeyeon has no transactions yet. */
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/clients/" + clientDTO.getName())
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.statement[0]").value("Heeyeon: $213.40")
        );
        
        
        /* Names of clients are case-sensitive. */


		clientDTO.setName("Heeyeon");
		clientDTO.setBalance(134.56);
		clientJson = objectMapper.writeValueAsString(clientDTO);
	
		// Add Client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Error: Client Heeyeon already exists")
        );
        
        
        // Get statement
        /* after an error, account statement stays unchanged */
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/clients/" + clientDTO.getName())
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.statement[0]").value("Heeyeon: $213.40")
        );
        
        
        
        /* deposit amount should be positive */
        TransactionDepositWithdrawDTO transactionDTO = TestDataUtil.createTransactionDepositA_DTO();
        transactionDTO.setTransactionType("DEPOSIT");
        transactionDTO.setAmount(-238.29);
        transactionDTO.setName("Heeyeon");
        String transactionJson = objectMapper.writeValueAsString(transactionDTO);
        
        // Deposit        
        
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + transactionDTO.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        );
        
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Error: Non-Positive Amount")
        );
        
        // Get statement
        /* after an error, account statement stays unchanged */
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/clients/" + clientDTO.getName())
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.statement[0]").value("Heeyeon: $213.40")
        );
        
        
        
        
        
        
        
        
       
        transactionDTO.setTransactionType("DEPOSIT");
        transactionDTO.setAmount(0);
        transactionDTO.setName("Heeyeon");
        transactionJson = objectMapper.writeValueAsString(transactionDTO);
        
        // Deposit        
        
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + transactionDTO.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        );
        
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Error: Non-Positive Amount")
        );
        
        // Get statement
        /* after an error, account statement stays unchanged */
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/clients/" + clientDTO.getName())
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.statement[0]").value("Heeyeon: $213.40")
        );
        
        
        
        
        
        
        
        
        
        
        
        
        
		clientDTO.setName("Jiyoon");
		clientDTO.setBalance(239.4);
		clientJson = objectMapper.writeValueAsString(clientDTO);
	
		// Add Client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
        
		clientDTO.setName("Sunhye");
		clientDTO.setBalance(332.6);
		clientJson = objectMapper.writeValueAsString(clientDTO);
	
		// Add Client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
        
		clientDTO.setName("Jihye");
		clientDTO.setBalance(428.8);
		clientJson = objectMapper.writeValueAsString(clientDTO);
	
		// Add Client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
        
        

        
        /* at this point, the bank is not full yet */


		clientDTO.setName("Jihye");
		clientDTO.setBalance(81.72);
		clientJson = objectMapper.writeValueAsString(clientDTO);
	
		// Add Client
		/* here Jihye is a duplicate name */
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Error: Client Jihye already exists")
        );
        
        // Get statement
        /* after an error, account statement stays unchanged */
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/clients/" + clientDTO.getName())
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.statement[0]").value("Jihye: $428.80")
        );
        
        
        
        
        
        /* Add clients to gradually */
        
        
		clientDTO.setName("Suyeon");
		clientDTO.setBalance(590.10);
		clientJson = objectMapper.writeValueAsString(clientDTO);
	
		// Add Client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
        
        
		clientDTO.setName("Yuna");
		clientDTO.setBalance(640.12);
		clientJson = objectMapper.writeValueAsString(clientDTO);
	
		// Add Client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Accounts: {Heeyeon: $213.40, Jiyoon: $239.40, Sunhye: $332.60, Jihye: $428.80, Suyeon: $590.10, Yuna: $640.12}")
        );
        
        
        
        
        
        
        
        
        
        

        // Get statement
 
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/clients/" + "Alan")
        );
        
        // Get bank status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Error: From-Account Alan does not exist")
        );
        
        
        


		
	}
	

	
	
	
	
	
	

	
	

}
