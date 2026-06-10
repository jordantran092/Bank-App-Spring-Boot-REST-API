package com.jordantran.bank_api.controllers;

import java.util.ArrayList;
import java.util.List;
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
	public ResponseEntity<TransactionDTO> depositOrWithdraw(@RequestBody TransactionDepositWithdrawDTO transactionDepositWithdrawDTO, @PathVariable("clientName") String clientName) {

		
		// Decided to re-use transaction object instead of making a separate object for this, contains all attributes we need. The rest of attributes can be left null since not needed
		
		ResponseEntity<TransactionDTO> result = null;
		 
		
		Optional<TransactionEntity> savedTransactionEntity = Optional.empty();
		
		
		if(transactionDepositWithdrawDTO.getTransactionType().equals("DEPOSIT")) {
			savedTransactionEntity = bankService.deposit(clientName, transactionDepositWithdrawDTO.getAmount());	
		}
		else if(transactionDepositWithdrawDTO.getTransactionType().equals("WITHDRAW")) {
			savedTransactionEntity = bankService.withdraw(clientName, transactionDepositWithdrawDTO.getAmount());	
		}
		
		
		if(savedTransactionEntity.isPresent()) {
			
			TransactionDTO savedTransactionDTO = transactionMapper.mapTo(savedTransactionEntity.get());
			result = new ResponseEntity<>(savedTransactionDTO, HttpStatus.OK);
		}
		else {
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		

		return result;
	
	}
	
	
	
	// bulk to still indicate updating single clients resource, but a bulk of client resources i.e. 2 clients declared in the json body
	@PatchMapping(path = "/api/v1/bank/clients/bulk")
	public ResponseEntity<List<TransactionDTO>> transfer(@RequestBody TransactionTransferDTO transactionTransferDTO) {

		
		ResponseEntity<List<TransactionDTO>> result = null;
		
		 
		Optional<List<TransactionEntity>> optionalSavedTransactionEntities = Optional.empty();
		
	
		optionalSavedTransactionEntities = bankService.transfer(transactionTransferDTO.getFromName(), transactionTransferDTO.getToName(), transactionTransferDTO.getAmount());	
	
		
		if(optionalSavedTransactionEntities.isPresent()) {
			
			List<TransactionDTO> savedTransactionDTOs = new ArrayList<>();
		
			
			for(TransactionEntity e : optionalSavedTransactionEntities.get()) {
				savedTransactionDTOs.add(transactionMapper.mapTo(e));
			}
			
			result = new ResponseEntity<>(savedTransactionDTOs, HttpStatus.OK);
		}
		else {
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		

		return result;
	
	}
	
	
	
	@GetMapping(path = "/api/v1/bank/clients/{clientName}")
	public ResponseEntity<GetStatementDTO> getStatement(@PathVariable("clientName") String clientName) {
		ResponseEntity<GetStatementDTO> result = null;
		
		
		Optional<List<String>> optionalStatement = bankService.getStatement(clientName);
		
		
		if(optionalStatement.isPresent()) {
			
			// Store the statement in the DTO for http response
			GetStatementDTO getStatementDTO = GetStatementDTO.builder()
					.statement(optionalStatement.get())
					.build();
			
			result = new ResponseEntity<>(getStatementDTO, HttpStatus.OK);
		}
		else {
			result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
		return result;
		
	}

	

	@GetMapping(path = "/api/v1/bank/status")
	public ResponseEntity<BankStatusDTO> getStatus() {
		ResponseEntity<BankStatusDTO> result = null;
		
		
		String status = bankService.getStatus();
		
		BankStatusDTO bankStatusDTO = BankStatusDTO.builder()
				.status(status)
				.build();
		
		result = new ResponseEntity<>(bankStatusDTO, HttpStatus.OK);
		
		return result;
		
	}
	
}
