package com.jordantran.bank_api.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jordantran.bank_api.domain.entities.*;
import com.jordantran.bank_api.repositories.*;

@ExtendWith(MockitoExtension.class)

class BankTests {

	
	@Mock
	BankRepository bankRepository;
	@Mock
	ClientService clientService;

	
	@InjectMocks
	BankService bankService;
	
   
    
    @Test
    public void test_03a() {
    	
		BankEntity bankEntity1 = BankEntity.builder()
				.id(0L)
				.error(false)
				.errorStr("")
				.build();
		
		Optional<BankEntity> optionalBankEntity1 = Optional.of(bankEntity1);
	    	
		when(bankRepository.findById(0L)).thenReturn(optionalBankEntity1);
    	when(clientService.findAll()).thenReturn(new ArrayList<ClientEntity>()); // because empty bank
        String status = bankService.getStatus();
        
        /* status of an empty bank */
        assertEquals("Accounts: {}", status);

        /* Print statement from a non-existing account. */
        
        
        // Don't need to because will return null anyways since mocked
//        when(clientService.getClient("Heeyeon")).thenReturn(null); 
//        when(bankRepository.save()).thenReturn(null);
        
        Optional<List<String>> heeyeonStmt = bankService.getStatement("Heeyeon");
        assertEquals(heeyeonStmt, Optional.empty());
        
        
		BankEntity bankEntity2 = BankEntity.builder()
				.id(0L)
				.error(true)
				.errorStr("Error: From-Account Heeyeon does not exist")
				.build();
		
		Optional<BankEntity> optionalBankEntity2 = Optional.of(bankEntity2);
		
		when(bankRepository.findById(0L)).thenReturn(optionalBankEntity2);
        assertEquals("Error: From-Account Heeyeon does not exist", bankService.getStatus());

//        /* deposit to a non-existing account */
//        b.deposit("Heeyeon", 300.05);
//        assertEquals("Error: To-Account Heeyeon does not exist", b.getStatus());
//        b.deposit("Jiyoon", -300.05); /* error of non-existing to-account takes priority (see error tables in PDF instructions) */
//        assertEquals("Error: To-Account Jiyoon does not exist", b.getStatus());
//
//        /* withdraw from a non-existing account */
//        b.withdraw("Heeyeon", 10.5);
//        assertEquals("Error: From-Account Heeyeon does not exist", b.getStatus());
//        b.withdraw("Jiyoon", -300.05); /* error of non-existing from-account takes priority (see error tables in PDF instructions) */
//        assertEquals("Error: From-Account Jiyoon does not exist", b.getStatus());
//
//        /* transfer between two non-existing accounts: error of from-account takes priority (see error tables in PDF instructions) */
//        b.transfer("Heeyeon", "Jiyoon", 100.24);
//        assertEquals("Error: From-Account Heeyeon does not exist", b.getStatus());
//        b.transfer("Jiyoon", "Heeyeon", -100.24);
//        assertEquals("Error: From-Account Jiyoon does not exist", b.getStatus());
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

    }
    


}
