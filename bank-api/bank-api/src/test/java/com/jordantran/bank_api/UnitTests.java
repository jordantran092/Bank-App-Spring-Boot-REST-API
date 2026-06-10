package com.jordantran.bank_api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jordantran.bank_api.domain.entities.TransactionEntity;
import com.jordantran.bank_api.repositories.*;
import com.jordantran.bank_api.services.*;

@ExtendWith(MockitoExtension.class)
class UnitTests {

	@Mock
	BankRepository bankRepository;
	@InjectMocks
	BankService bankService;
	
	
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
    				.id(0L)
    				.transactionType("DEPOSIT")
    				.amount(100.5)
    				.clientEntity(null)
    				.build()
    			
    			);
    	
    	
    	when(transactionService.findById(0L)).thenReturn(optionalTransactionEntity);
    	
    	
    	assertEquals("Transaction DEPOSIT: $100.50", transactionService.getStatus(0L));
    	
    	
    	/*
        Transaction t1 = new Transaction("DEPOSIT", 100.5);
        assertEquals("Transaction DEPOSIT: $100.50", t1.getStatus());

        Transaction t2 = new Transaction("WITHDRAW", 250);
        assertEquals("Transaction WITHDRAW: $250.00", t2.getStatus());
        */
    }

}
