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

class ClientTests {

	
	@Mock
	ClientRepository clientRepository;
	@Mock
	TransactionService transactionService;

	
	@InjectMocks
	ClientService clientService;
	
    

    @Test
    public void test_02a() {
    	
    	
    	
    	ClientEntity heeyeonEntity = ClientEntity.builder()
    				.id(1L)
    				.name("Heeyeon")
    				.balance(100.5)
    				.bankEntity(null)
    				.build();
    	
    	Long heeyeonID = heeyeonEntity.getId();
    	
    	
    	Optional<ClientEntity> heeyeon = Optional.of(heeyeonEntity);

    	
        /*
         * A client's status displays their name and current account balance.
         */
    	when(clientRepository.findById(1L)).thenReturn(heeyeon);
        String status = clientService.getStatus(heeyeonID);
        assertEquals("Heeyeon: $100.50", status);
        
        
        /*
         * A client's statement summarizes
         * their current status followed by their history list of transactions.
         */
        when(transactionService.findAll()).thenReturn(new ArrayList<>());
        List<String> stmt = clientService.getStatement(heeyeonID);
        List<String> expectedStmt1 = new ArrayList<>(List.of(clientService.getStatus(heeyeonID)));
        assertTrue(expectedStmt1.size() == 1); /* just the status */
        assertEquals(expectedStmt1, stmt);


        /* Assume: deposit amount always positive. No error checking needed.  */
       	TransactionEntity t1 = TransactionEntity.builder()
				.id(1L)
				.transactionType("DEPOSIT")
				.amount(20.3)
				.clientEntity(heeyeonEntity)
				.build();
       	
       	
       	

        clientService.deposit(heeyeonID, 20.3); // No need mock .createTransaction which is return value, not used
        assertEquals("Heeyeon: $120.80", clientService.getStatus(heeyeonID));
        List<String> expectedStmt2 = new ArrayList<>(List.of(
        		clientService.getStatus(heeyeonID), 
        		"Transaction DEPOSIT: $20.30"
        		));
        assertTrue(expectedStmt2.size() == 2); /* status and one transaction */
        
        

        when(transactionService.findAll()).thenReturn(new ArrayList<TransactionEntity>(
        		List.of(t1)
        ));
        when(transactionService.getStatus(1L)).thenReturn("Transaction DEPOSIT: $20.30");
        assertEquals(expectedStmt2, clientService.getStatement(heeyeonID));
    	

        
        
        
        
        /* Assume: withdraw amount always positive and not too large. No error checking needed. */
        
       	TransactionEntity t2 = TransactionEntity.builder()
				.id(2L)
				.transactionType("WITHDRAW")
				.amount(40.78)
				.clientEntity(heeyeonEntity)
				.build();
       	
        clientService.withdraw(heeyeonID, 40.78); // No need mock .createTransaction which is return value, not used
        assertEquals("Heeyeon: $80.02", clientService.getStatus(heeyeonID));
        List<String> expectedStmt3 = new ArrayList<>(List.of(
        		clientService.getStatus(heeyeonID), 
        		"Transaction DEPOSIT: $20.30",
        		"Transaction WITHDRAW: $40.78"
        ));
        assertTrue(expectedStmt3.size() == 3); /* status and two transactions */
        when(transactionService.findAll()).thenReturn(new ArrayList<TransactionEntity>(
        		List.of(t1, t2)
        ));
        when(transactionService.getStatus(1L)).thenReturn("Transaction DEPOSIT: $20.30");
        when(transactionService.getStatus(2L)).thenReturn("Transaction WITHDRAW: $40.78");
        assertEquals(expectedStmt3, clientService.getStatement(heeyeonID));
        

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Test
    public void test_02b() {
	    	
    	ClientEntity jiyoonEntity = ClientEntity.builder()
				.id(1L)
				.name("Jiyoon")
				.balance(89.5)
				.bankEntity(null)
				.build();
		
		Long jiyoonID = jiyoonEntity.getId();
		
		
		Optional<ClientEntity> jiyoon = Optional.of(jiyoonEntity);
	
		
	    /*
	     * A client's status displays their name and current account balance.
	     */
		when(clientRepository.findById(1L)).thenReturn(jiyoon);
	    String status = clientService.getStatus(jiyoonID);
	    assertEquals("Jiyoon: $89.50", status);
	    
	    
	    /*
	     * A client's statement summarizes
	     * their current status followed by their history list of transactions.
	     */
	    when(transactionService.findAll()).thenReturn(new ArrayList<>());
	    List<String> stmt = clientService.getStatement(jiyoonID);
	    List<String> expectedStmt1 = new ArrayList<>(List.of(clientService.getStatus(jiyoonID)));
	    assertTrue(expectedStmt1.size() == 1); /* just the status */
	    assertEquals(expectedStmt1, stmt);
	
	
	    /* Assume: withdraw amount always positive and not too large. No error checking needed. */
	   	
	
	    clientService.withdraw(jiyoonID, 40.78); // No need mock .createTransaction which is return value, not used
	    assertEquals("Jiyoon: $48.72", clientService.getStatus(jiyoonID));
	    List<String> expectedStmt2 = new ArrayList<>(List.of(
	    		clientService.getStatus(jiyoonID), 
	    		"Transaction WITHDRAW: $40.78"
	    		));
	    assertTrue(expectedStmt2.size() == 2); /* status and one transaction */
	    
	    
	
	   	TransactionEntity t1 = TransactionEntity.builder()
				.id(1L)
				.transactionType("WITHDRAW")
				.amount(40.78)
				.clientEntity(jiyoonEntity)
				.build();
	    when(transactionService.findAll()).thenReturn(new ArrayList<TransactionEntity>(
	    		List.of(t1)
	    ));
	    when(transactionService.getStatus(1L)).thenReturn("Transaction WITHDRAW: $40.78");
	    assertEquals(expectedStmt2, clientService.getStatement(jiyoonID));
		
	
	    
	    
	    
	    
	    /* Assume: deposit amount always positive. No error checking needed.  */
       	TransactionEntity t2 = TransactionEntity.builder()
				.id(2L)
				.transactionType("DEPOSIT")
				.amount(20.3)
				.clientEntity(jiyoonEntity)
				.build();
       	
        clientService.deposit(jiyoonID, 20.3); // No need mock .createTransaction which is return value, not used
        assertEquals("Jiyoon: $69.02", clientService.getStatus(jiyoonID));
        List<String> expectedStmt3 = new ArrayList<>(List.of(
        		clientService.getStatus(jiyoonID), 
        		"Transaction WITHDRAW: $40.78", 
        		"Transaction DEPOSIT: $20.30"
        ));
        assertTrue(expectedStmt3.size() == 3); /* status and two transactions */
        when(transactionService.findAll()).thenReturn(new ArrayList<TransactionEntity>(
        		List.of(t1, t2)
        ));
        when(transactionService.getStatus(1L)).thenReturn("Transaction WITHDRAW: $40.78");
        when(transactionService.getStatus(2L)).thenReturn("Transaction DEPOSIT: $20.30");
        assertEquals(expectedStmt3, clientService.getStatement(jiyoonID));
    	
    	


    }


}
