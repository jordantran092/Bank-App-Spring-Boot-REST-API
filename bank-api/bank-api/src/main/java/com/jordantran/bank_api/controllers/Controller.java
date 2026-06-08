package com.jordantran.bank_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.jordantran.bank_api.components.mappers.Mapper;
import com.jordantran.bank_api.domain.dto.ClientDTO;
import com.jordantran.bank_api.domain.entities.ClientEntity;
import com.jordantran.bank_api.services.*;


@RestController
public class Controller {
	
	private Mapper<ClientEntity, ClientDTO> clientMapper;
	private BankService bankService;
	
	public Controller(Mapper<ClientEntity, ClientDTO> clientMapper, BankService bankService) {
		this.clientMapper = clientMapper;
		this.bankService = bankService;
	}

	@PostMapping(path = "/api/v1/bank")
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
	
	@PatchMapping(path = "/api/v1/bank/{client_name}")
	public ResponseEntity<TransactionDTO> deposit(@RequestBody TransactionDTO transactionDTO) {
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
}
