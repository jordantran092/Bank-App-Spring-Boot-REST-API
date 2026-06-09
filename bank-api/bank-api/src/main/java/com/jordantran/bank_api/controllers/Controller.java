package com.jordantran.bank_api.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.jordantran.bank_api.components.mappers.Mapper;
import com.jordantran.bank_api.domain.dto.*;
import com.jordantran.bank_api.domain.entities.*;
import com.jordantran.bank_api.services.*;


@RestController
public class Controller {
	
	private Mapper<ClientEntity, ClientDTO> clientMapper;
	private Mapper<TransactionEntity, TransactionDTO> transactionMapper;
	private BankService bankService;
	
	public Controller(Mapper<ClientEntity, ClientDTO> clientMapper, Mapper<TransactionEntity, TransactionDTO> transactionMapper, BankService bankService) {
		this.clientMapper = clientMapper;
		this.bankService = bankService;
		this.transactionMapper = transactionMapper;
	}

	@PostMapping(path = "/api/v1/bank/clients")
	public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO clientDTO) {
		/*
		 * bank.addclient will handle the business logic, not here
		 * 
		 * convert dto to entity
		 * input into bank.addclient 
		 * return result of added client 
		 * convert back to dto
		 * return dto
		 */
		
		ResponseEntity<ClientDTO> result = null;
		
		
		ClientEntity clientEntity = clientMapper.mapFrom(clientDTO); 
		
		ClientEntity savedClientEntity = bankService.addClient(clientEntity);
		
		
		if(savedClientEntity != null) {
			ClientDTO savedClientDTO = clientMapper.mapTo(savedClientEntity);
			result = new ResponseEntity<>(savedClientDTO, HttpStatus.CREATED);
		}
		else {
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		return result;
	
	}
	
	@PatchMapping(path = "/api/v1/bank/clients/{clientName}")
	public ResponseEntity<TransactionDTO> deposit(@RequestBody TransactionDTO transactionDTO, @PathVariable("clientName") String clientName) {
		/*
		 * Just re-use transaction entity, contains all the attributes you need, and in the http request leave the unnecessary attributes null. Return the updated client balance in the transaction (should be nested by default), can use for assertions
		 */
		
		// Decided to re-use transaction object instead of making a separate object for this, contains all attributes we need. The rest of attributes can be left null since not needed
		
		ResponseEntity<TransactionDTO> result = null;
		
		
		TransactionEntity transactionEntity = transactionMapper.mapFrom(transactionDTO); 
		
		Optional<TransactionEntity> savedTransactionEntity = bankService.deposit(clientName, transactionEntity);
		
		if(savedTransactionEntity.isPresent()) {
			
			TransactionDTO savedTransactionDTO = transactionMapper.mapTo(savedTransactionEntity.get());
			result = new ResponseEntity<>(savedTransactionDTO, HttpStatus.OK);
		}
		else {
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		

		return result;
	
	}
}
