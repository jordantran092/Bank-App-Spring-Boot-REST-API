







// PROBABLY DELETE THIS LATER


//package com.jordantran.bank_api.services;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import static org.mockito.Mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.jordantran.bank_api.domain.entities.*;
//import com.jordantran.bank_api.repositories.*;
//
//@ExtendWith(MockitoExtension.class)
//
//class BankTests {
//
//	
//	@Mock
//	BankRepository bankRepository;
//	@Mock
//	ClientService clientService;
//
//	
//	@InjectMocks
//	BankService bankService;
//	
//   
//    
//    @Test
//    public void test_03a() {
//    	
//		BankEntity bankEntity = BankEntity.builder()
//				.id(0L)
//				.error(false)
//				.errorStr("")
//				.build();
//		
//		Optional<BankEntity> optionalBankEntity = Optional.of(bankEntity);
//	    	
//		when(bankRepository.findById(0L)).thenReturn(optionalBankEntity);
//    	when(clientService.findAll()).thenReturn(new ArrayList<ClientEntity>()); // because empty bank
//        String status = bankService.getStatus();
//        
//        /* status of an empty bank */
//        assertEquals("Accounts: {}", status);
//
//        /* Print statement from a non-existing account. */
//        
//        
//        // For getStatement, don't need to return null from mocked methods explicitly because will return null by default
//        Optional<List<String>> heeyeonStmt = bankService.getStatement("Heeyeon");
//        // .getStatement will turn on error
//        bankEntity.setError(true);
//        bankEntity.setErrorStr("Error: From-Account Heeyeon does not exist");
//        assertEquals(heeyeonStmt, Optional.empty());
//        assertEquals("Error: From-Account Heeyeon does not exist", bankService.getStatus());
//
//        
//        
//        
//        /* deposit to a non-existing account */
//        bankService.deposit("Heeyeon", 300.05); // Mocked methods will return null
//        bankEntity.setError(true);
//        bankEntity.setErrorStr("Error: To-Account Heeyeon does not exist");
//        assertEquals("Error: To-Account Heeyeon does not exist", bankService.getStatus());
//        
//        bankService.deposit("Jiyoon", -300.05); /* error of non-existing to-account takes priority */
//        bankEntity.setError(true);
//        bankEntity.setErrorStr("Error: To-Account Jiyoon does not exist");
//        assertEquals("Error: To-Account Jiyoon does not exist", bankService.getStatus());
//
//        /* withdraw from a non-existing account */
//        bankService.withdraw("Heeyeon", 10.5);
//        bankEntity.setError(true);
//        bankEntity.setErrorStr("Error: From-Account Heeyeon does not exist");
//        assertEquals("Error: From-Account Heeyeon does not exist", bankService.getStatus());
//        bankService.withdraw("Jiyoon", -300.05); /* error of non-existing from-account takes priority  */
//        bankEntity.setError(true);
//        bankEntity.setErrorStr("Error: From-Account Jiyoon does not exist");
//        assertEquals("Error: From-Account Jiyoon does not exist", bankService.getStatus());
//
//        /* transfer between two non-existing accounts: error of from-account takes priority */
//        bankService.transfer("Heeyeon", "Jiyoon", 100.24);
//        
//        assertEquals("Error: From-Account Heeyeon does not exist", bankService.getStatus());
//        bankService.transfer("Jiyoon", "Heeyeon", -100.24);
//        assertEquals("Error: From-Account Jiyoon does not exist", bankService.getStatus());
//
//        
//        
//        //----
//        
//    	ClientEntity jiyoonEntity = ClientEntity.builder()
//				.id(1L)
//				.name("Jiyoon")
//				.balance(89.5)
//				.bankEntity(null)
//				.build();
//		
//		Long jiyoonID = jiyoonEntity.getId();
//		
//		
//		Optional<ClientEntity> jiyoon = Optional.of(jiyoonEntity);
//	
//		
//	    /*
//	     * A client's status displays their name and current account balance.
//	     */
//		when(clientRepository.findById(1L)).thenReturn(jiyoon);
//	    String status = clientService.getStatus(jiyoonID);
//	    assertEquals("Jiyoon: $89.50", status);
//	    
//	    
//	    /*
//	     * A client's statement summarizes
//	     * their current status followed by their history list of transactions.
//	     */
//	    when(transactionService.findAll()).thenReturn(new ArrayList<>());
//	    List<String> stmt = clientService.getStatement(jiyoonID);
//	    List<String> expectedStmt1 = new ArrayList<>(List.of(clientService.getStatus(jiyoonID)));
//	    assertTrue(expectedStmt1.size() == 1); /* just the status */
//	    assertEquals(expectedStmt1, stmt);
//	
//	
//	    /* Assume: withdraw amount always positive and not too large. No error checking needed. */
//	   	
//	
//	    clientService.withdraw(jiyoonID, 40.78); // No need mock .createTransaction which is return value, not used
//	    assertEquals("Jiyoon: $48.72", clientService.getStatus(jiyoonID));
//	    List<String> expectedStmt2 = new ArrayList<>(List.of(
//	    		clientService.getStatus(jiyoonID), 
//	    		"Transaction WITHDRAW: $40.78"
//	    		));
//	    assertTrue(expectedStmt2.size() == 2); /* status and one transaction */
//	    
//	    
//	
//	   	TransactionEntity t1 = TransactionEntity.builder()
//				.id(1L)
//				.transactionType("WITHDRAW")
//				.amount(40.78)
//				.clientEntity(jiyoonEntity)
//				.build();
//	    when(transactionService.findAll()).thenReturn(new ArrayList<TransactionEntity>(
//	    		List.of(t1)
//	    ));
//	    when(transactionService.getStatus(1L)).thenReturn("Transaction WITHDRAW: $40.78");
//	    assertEquals(expectedStmt2, clientService.getStatement(jiyoonID));
//		
//	
//	    
//	    
//	    
//	    
//	    /* Assume: deposit amount always positive. No error checking needed.  */
//       	TransactionEntity t2 = TransactionEntity.builder()
//				.id(2L)
//				.transactionType("DEPOSIT")
//				.amount(20.3)
//				.clientEntity(jiyoonEntity)
//				.build();
//       	
//        clientService.deposit(jiyoonID, 20.3); // No need mock .createTransaction which is return value, not used
//        assertEquals("Jiyoon: $69.02", clientService.getStatus(jiyoonID));
//        List<String> expectedStmt3 = new ArrayList<>(List.of(
//        		clientService.getStatus(jiyoonID), 
//        		"Transaction WITHDRAW: $40.78", 
//        		"Transaction DEPOSIT: $20.30"
//        ));
//        assertTrue(expectedStmt3.size() == 3); /* status and two transactions */
//        when(transactionService.findAll()).thenReturn(new ArrayList<TransactionEntity>(
//        		List.of(t1, t2)
//        ));
//        when(transactionService.getStatus(1L)).thenReturn("Transaction WITHDRAW: $40.78");
//        when(transactionService.getStatus(2L)).thenReturn("Transaction DEPOSIT: $20.30");
//        assertEquals(expectedStmt3, clientService.getStatement(jiyoonID));
//
//    }
//    
//
//
//}
