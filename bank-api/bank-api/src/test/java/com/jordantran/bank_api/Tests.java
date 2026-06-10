package com.jordantran.bank_api;

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
import com.jordantran.bank_api.services.*;

@ExtendWith(MockitoExtension.class)
class Tests {

	@Mock
	BankRepository bankRepository;
	@InjectMocks
	BankService bankService;
	
	
	@Mock 
	TransactionRepository transactionRepository;
	@InjectMocks
	TransactionService transactionService;
	
	
	@Mock 
	ClientRepository clientRepository;
	@InjectMocks
	ClientService clientService;
	
	
	
    @Test
    public void test_01() {
        /* 
         *
         * 
         */
    	
    	Optional<TransactionEntity> optionalTransactionEntity = Optional.of(
    			
    			TransactionEntity.builder()
    				.id(1L)
    				.transactionType("DEPOSIT")
    				.amount(100.5)
    				.clientEntity(null)
    				.build()
    			
    			);
    	
    	Optional<TransactionEntity> optionalTransactionEntityB = Optional.of(
    			
    			TransactionEntity.builder()
    				.id(2L)
    				.transactionType("WITHDRAW")
    				.amount(250)
    				.clientEntity(null)
    				.build()
    			
    			);
    	
    	
    	when(transactionRepository.findById(1L)).thenReturn(optionalTransactionEntity);
    	when(transactionRepository.findById(2L)).thenReturn(optionalTransactionEntityB);
    	
    	    	
    	assertEquals("Transaction DEPOSIT: $100.50", transactionService.getStatus(1L));
    	assertEquals("Transaction WITHDRAW: $250.00", transactionService.getStatus(2L));
    	
    	
    }
    
    

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
        List<String> stmt = clientService.getStatement(heeyeonID);
        List<String> expectedStmt1 = new ArrayList<>(List.of(clientService.getStatus(heeyeonID)));
        assertTrue(expectedStmt1.size() == 1); /* just the status */
        assertTrue(expectedStmt1.equals(stmt));


        /* Assume: deposit amount always positive. No error checking needed.  */
        clientService.deposit(heeyeonID, 20.3);
        assertEquals("Heeyeon: $120.80", clientService.getStatus(heeyeonID));
        List<String> expectedStmt2 = new ArrayList<>(List.of(
        		clientService.getStatus(heeyeonID), 
        		"Transaction DEPOSIT: $20.30"
        		));
        assertTrue(expectedStmt2.size() == 2); /* status and one transaction */
        assertTrue(expectedStmt2.equals(clientService.getStatement(heeyeonID)));
    	

        /* Assume: withdraw amount always positive and not too large. No error checking needed. */
        clientService.withdraw(heeyeonID, 40.78);
        assertEquals("Heeyeon: $80.02", clientService.getStatus(heeyeonID));
        List<String> expectedStmt3 = new ArrayList<>(List.of(
        		clientService.getStatus(heeyeonID), 
        		"Transaction DEPOSIT: $20.30",
        		"Transaction WITHDRAW: $40.78"
        ));
        assertTrue(expectedStmt3.size() == 3); /* status and two transactions */
        assertTrue(expectedStmt3.equals(clientService.getStatement(heeyeonID)));
        

    }

}
