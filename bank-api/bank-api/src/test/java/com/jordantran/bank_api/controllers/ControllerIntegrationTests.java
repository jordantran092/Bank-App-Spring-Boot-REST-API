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
import com.jordantran.bank_api.domain.dto.*;
import com.jordantran.bank_api.services.*;




@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class ControllerIntegrationTests {
	
	private ObjectMapper objectMapper;
	private MockMvc mockMvc;
	
	@Autowired
	public ControllerIntegrationTests(BankService bankService, ObjectMapper objectMapper, MockMvc mockMvc) {
		this.objectMapper = new ObjectMapper();
		this.mockMvc = mockMvc;
	}
	
	
	//----------------------------------
	
	
	/* Get Bank Status */
	
	@Test
	void testThatGetBankStatusReturnsHttp200() throws Exception {


        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		// check status of http response result for 200 ok
                MockMvcResultMatchers.status().isOk()
        );

	}
	
	@Test
	void testThatGetBankStatusReturnsSuccessfullyWithNoClients() throws Exception {

        
        
        // Get bank status
        
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Accounts: {}")
        );

    
	}
	
	@Test
	void testThatGetBankStatusReturnsSuccessfullyWithDeposit() throws Exception {


		ClientDTO clientDTO = TestDataUtil.createClientA_DTO();
		String clientJson = objectMapper.writeValueAsString(clientDTO);
		
		
		TransactionDepositWithdrawDTO transactionDTO = TestDataUtil.createTransactionDepositA_DTO();
		String transactionJson = objectMapper.writeValueAsString(transactionDTO);
	
		
		// Create client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
		
		
        // Deposit
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + clientDTO.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        );
        
        
        // Get bank status
        
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/status")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.status").value("Accounts: {John: $30.50}")
        );

    
	}
	
	
	
	/* Get Statement */
	
	@Test
	void testThatGetStatementReturnsHttp200() throws Exception {


		
		ClientDTO clientDTO = TestDataUtil.createClientA_DTO();
		String clientJson = objectMapper.writeValueAsString(clientDTO);
		
		
		TransactionDepositWithdrawDTO transactionDTO = TestDataUtil.createTransactionDepositA_DTO();
		String transactionJson = objectMapper.writeValueAsString(transactionDTO);
	
		
		// Create client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
		
		
        // Deposit
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + clientDTO.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        );
        
        
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/clients/" + clientDTO.getName())
        ).andExpect(
        		// check status of http response result for 200 ok
                MockMvcResultMatchers.status().isOk()
        );

	}
	
	@Test
	void testThatGetStatementReturnsStatement() throws Exception {


		
		ClientDTO clientDTO = TestDataUtil.createClientA_DTO();
		String clientJson = objectMapper.writeValueAsString(clientDTO);
		
		
		TransactionDepositWithdrawDTO transactionDTO = TestDataUtil.createTransactionDepositA_DTO();
		String transactionJson = objectMapper.writeValueAsString(transactionDTO);
	
		
		// Create client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
		
		
        // Deposit
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + clientDTO.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        );
        
        
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/clients/" + clientDTO.getName())
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.statement[0]").value("John: $30.50")
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.statement[1]").value("Transaction DEPOSIT: $10.20") 
        );
        
        

	}
	
	
	@Test
	void testThatGetStatementReturnsHttp404WhenClientNotExist() throws Exception {


		
		ClientDTO clientDTO = TestDataUtil.createClientA_DTO();
		
	
        
        
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bank/clients/" + clientDTO.getName())
        ).andExpect(
        		// check status of http response result for http conflict
                MockMvcResultMatchers.status().isNotFound()
        );

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* Transfers */
	
	@Test
	void testThatTransferReturnsHttp200() throws Exception {

	
		
		ClientDTO clientA_DTO = TestDataUtil.createClientA_DTO();
		String clientA_Json = objectMapper.writeValueAsString(clientA_DTO);
		
		ClientDTO clientB_DTO = TestDataUtil.createClientB_DTO();
		String clientB_Json = objectMapper.writeValueAsString(clientB_DTO);
		
		
		TransactionTransferDTO transactionDTO = TestDataUtil.createTransactionTransferA_DTO();
		String transactionJson = objectMapper.writeValueAsString(transactionDTO);
	
		
		// Create client A
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientA_Json)
        );
        
		// Create client B
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientB_Json)
        );
		
		
        // Transfer
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        ).andExpect(
        		// check status of http response result for 200 ok
                MockMvcResultMatchers.status().isOk()
        );
	}
	
	
	@Test
	void testThatTransferReturnsSavedTransactions() throws Exception {

	
		
		ClientDTO clientA_DTO = TestDataUtil.createClientA_DTO();
		String clientA_Json = objectMapper.writeValueAsString(clientA_DTO);
		
		ClientDTO clientB_DTO = TestDataUtil.createClientB_DTO();
		String clientB_Json = objectMapper.writeValueAsString(clientB_DTO);
		
		
		TransactionTransferDTO transactionDTO = TestDataUtil.createTransactionTransferA_DTO();
		String transactionJson = objectMapper.writeValueAsString(transactionDTO);
	
		
		// Create client A
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientA_Json)
        );
        
		// Create client B
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientB_Json)
        );
		
		
        // Transfer
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$[0].transactionType").value("WITHDRAW")  
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$[0].amount").value(5)
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$[0].clientDTO.name").value("John")
        		
        		
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$[1].id").isNumber()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$[1].transactionType").value("DEPOSIT")  
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$[1].amount").value(5)
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$[1].clientDTO.name").value("Bob")
        );
	}
	
	@Test
	void testThatTransferReturnsHttpConflictWhenReceiverClientNotExist() throws Exception {

	
		
		ClientDTO clientA_DTO = TestDataUtil.createClientA_DTO();
		String clientA_Json = objectMapper.writeValueAsString(clientA_DTO);
		
		
		
		TransactionTransferDTO transactionDTO = TestDataUtil.createTransactionTransferA_DTO();
		String transactionJson = objectMapper.writeValueAsString(transactionDTO);
	
		
		// Create client A
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientA_Json)
        );
      
		
		
        // Transfer
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        ).andExpect(
        		// check status of http response result for http conflict
                MockMvcResultMatchers.status().isConflict()
        );
	}

	
	
	
	
	

	
	
	
	/* Withdrawals */
	
	@Test
	void testThatWithdrawReturnsHttp200() throws Exception {

		
		// No need bank dto, can leave null, testing status
		
		ClientDTO clientDTO = TestDataUtil.createClientA_DTO();
		String clientJson = objectMapper.writeValueAsString(clientDTO);
		
		
		TransactionDepositWithdrawDTO transactionDTO = TestDataUtil.createTransactionWithdrawA_DTO();
		String transactionJson = objectMapper.writeValueAsString(transactionDTO);
	
		
		// Create client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
		
		
        // Withdraw
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + clientDTO.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        ).andExpect(
        		// check status of http response result for 200 ok
                MockMvcResultMatchers.status().isOk()
        );
	}
	
	@Test
	void testThatWithdrawReturnsSavedTransactionWithdraw() throws Exception {

		
		ClientDTO clientDTO = TestDataUtil.createClientA_DTO();
		String clientJson = objectMapper.writeValueAsString(clientDTO);
		
		
		TransactionDepositWithdrawDTO transactionDTO = TestDataUtil.createTransactionWithdrawA_DTO();
		String transactionJson = objectMapper.writeValueAsString(transactionDTO);
	
		
		// Create client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
		
		
        // Withdraw
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + clientDTO.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.transactionType").value("WITHDRAW")  
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.amount").value(5)
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.clientDTO.name").value("John")
        );
	}
	
	
	
	
	@Test
	void testThatWithdrawReturnsHttpConflictWhenClientNotExist() throws Exception {

		
		// No need bank dto, can leave null
		

		
		
		TransactionDepositWithdrawDTO transactionDTO = TestDataUtil.createTransactionWithdrawA_DTO();
		String transactionJson = objectMapper.writeValueAsString(transactionDTO);
		
		
        // Deposit
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + transactionDTO.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        ).andExpect(
        		// check status of http response result for http conflict
                MockMvcResultMatchers.status().isConflict()
        );
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* Deposits */
	
	@Test
	void testThatDepositReturnsHttp200() throws Exception {

		
		// No need bank dto, can leave null
		
		ClientDTO clientDTO = TestDataUtil.createClientA_DTO();
		String clientJson = objectMapper.writeValueAsString(clientDTO);
		
		
		TransactionDepositWithdrawDTO transactionDTO = TestDataUtil.createTransactionDepositA_DTO();
		String transactionJson = objectMapper.writeValueAsString(transactionDTO);
	
		
		// Create client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
		
		
        // Deposit
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + clientDTO.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        ).andExpect(
        		// check status of http response result for 200 ok
                MockMvcResultMatchers.status().isOk()
        );
	}
	
	@Test
	void testThatDepositReturnsSavedTransactionDeposit() throws Exception {


		
		// No need bank dto, can leave null
		
		ClientDTO clientDTO = TestDataUtil.createClientA_DTO();
		String clientJson = objectMapper.writeValueAsString(clientDTO);
		
		
		TransactionDepositWithdrawDTO transactionDTO = TestDataUtil.createTransactionDepositA_DTO();
		String transactionJson = objectMapper.writeValueAsString(transactionDTO);
	
		
		// Create client
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
		
		
        // Deposit
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + clientDTO.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.transactionType").value("DEPOSIT")  
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.amount").value(10.2)
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.clientDTO.name").value("John")
        );
	}
	
	
	@Test
	void testThatDepositReturnsHttpConflictWhenClientNotExist() throws Exception {

		
		// No need bank dto, can leave null

		
		
		TransactionDepositWithdrawDTO transactionDTO = TestDataUtil.createTransactionDepositA_DTO();
		String transactionJson = objectMapper.writeValueAsString(transactionDTO);
		
		
        // Deposit
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.patch("/api/v1/bank/clients/" + transactionDTO.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        ).andExpect(
        		// check status of http response result for http conflict
                MockMvcResultMatchers.status().isConflict()
        );
	}
	
	
	
	
	
	
	
	
	
	/* Client Creation */
	
	
	@Test
	void testThatClientCreationReturnsHttp201() throws Exception {

		
		BankDTO bankDTO = TestDataUtil.createBank_DTO();
		ClientDTO clientDTO = TestDataUtil.createClientA_DTO(bankDTO);
		
		
		String clientJson = objectMapper.writeValueAsString(clientDTO);
	
		
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        ).andExpect(
        		// check status of http response result for 201 created
                MockMvcResultMatchers.status().isCreated()
        );
	}
	
	@Test
	void testThatClientCreationReturnsSavedClient() throws Exception {

		
		BankDTO bankDTO = TestDataUtil.createBank_DTO();
		ClientDTO clientDTO = TestDataUtil.createClientA_DTO(bankDTO);
		
		
		String clientJson = objectMapper.writeValueAsString(clientDTO);
	
		
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber() // auto generated, so as long as number, good
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("John")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.balance").value(20.3)
        ).andExpect(
        		// bankDTO is a nested object, requires dot navigation
                MockMvcResultMatchers.jsonPath("$.bankDTO.error").value(false)
		).andExpect(
                MockMvcResultMatchers.jsonPath("$.bankDTO.errorStr").value("")
        );
	}
	
	
	@Test
	void testThatClientCreationReturnsHttpConflictWhenClientExists() throws Exception {

		
		BankDTO bankDTO = TestDataUtil.createBank_DTO();
		ClientDTO clientDTO = TestDataUtil.createClientA_DTO(bankDTO);
		ClientDTO clientDTO2 = TestDataUtil.createClientA_DTO(bankDTO);
		
		
		String clientJson = objectMapper.writeValueAsString(clientDTO);
		String clientJson2 = objectMapper.writeValueAsString(clientDTO2);
	
		
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        );
        
        mockMvc.perform(
        		// call post method, with type json, and json body
                MockMvcRequestBuilders.post("/api/v1/bank/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson2)
        ).andExpect(
        		// check status of http response result for conflict
                MockMvcResultMatchers.status().isConflict()
        );
	}
	
	
	
	
	
	
	

	
	

}
