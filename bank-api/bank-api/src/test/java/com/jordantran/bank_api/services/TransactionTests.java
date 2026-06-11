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

class TransactionTests {


	@Mock
	TransactionRepository transactionRepository;
	


	@InjectMocks
	TransactionService transactionService;
	
	

	
	
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
    
    


}
