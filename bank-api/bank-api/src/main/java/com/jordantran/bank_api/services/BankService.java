package com.jordantran.bank_api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jordantran.bank_api.domain.entities.BankEntity;
import com.jordantran.bank_api.domain.entities.ClientEntity;
import com.jordantran.bank_api.domain.entities.TransactionEntity;
import com.jordantran.bank_api.repositories.BankRepository;

@Service
public class BankService {


	
	private BankRepository bankRepository;
	private ClientService clientService;
	
	public BankService(BankRepository bankRepository, ClientService clientService) {
		this.bankRepository = bankRepository;
		this.clientService = clientService;
	}
	
	public BankEntity createUpdateBank(BankEntity bankEntity) {
		return bankRepository.save(bankEntity);
	}
	
	
	public long count() {
		return bankRepository.count();
	}

	
	/*
	 *  Will check if any of the errors will occur, if not then will create a new client with the name and initial balance and clear any previous bank service errors. Errors are prioritized in a way where the lowest ranked error is outputted if multiple errors occur, thus the higher in the if chain, the more priority. Error checks are if max number of accounts reached, if client already exists, and if non-positive initial balance
	 */
	public ClientEntity addClient(ClientEntity clientEntity) {

		clientEntity.setId(null); // Set null to avoid hibernate thinking the id exists if not null and giving error, in case DTO had an id
		
		ClientEntity savedClientEntity = null;
		
		String name = clientEntity.getName();
		double amount = clientEntity.getBalance();
		
		
		/*
		 * higher prio is higher in the if chain, then just do else if.... as you go down, to create the priority 
		 * 
		 * 
		 * Should handle input validation here because need to turnOnError as well, so not helpful to handle input validation in controller
		 */
		if(clientService.getClient(name) != null) { //rank 1
			turnOnError(String.format("Error: Client %s already exists", name));
		}
		else if(amount <= 0) { //rank 2
			turnOnError("Error: Non-Positive Initial Balance");
		}
		else {
			
//			clientEntity.setNumOfTransactions(0L);
			clientEntity.setBankEntity(bankRepository.findById(0L).get()); // value in the optional bank object
			
			savedClientEntity = clientService.save(clientEntity);
			
			turnOffError();
		}
		
		
		// return
		return savedClientEntity;
		
		
		
		
	}
	
	
	public Optional<TransactionEntity> withdraw(String clientName, double amount) {


		
		
		Optional<TransactionEntity> savedTransactionEntity = Optional.empty();
		
		
		ClientEntity client = clientService.getClient(clientName);
		
		if(client == null) { //rank1
			turnOnError(String.format("Error: From-Account %s does not exist", clientName));
		}
		else if(amount <= 0) { //rank 2
			turnOnError("Error: Non-Positive Amount");
		}
		else if(amount > client.getBalance()) {
			turnOnError("Error: Amount too large to withdraw");
		}
		else {

			
			
			savedTransactionEntity = Optional.of(clientService.withdraw(client.getId(), amount));
			
			turnOffError();
		}
		
		
		return savedTransactionEntity;
		
	}
	
	public Optional<TransactionEntity> deposit(String clientName, double amount) {

		
	
		
		Optional<TransactionEntity> savedTransactionEntity = Optional.empty();
		
		
		ClientEntity client = clientService.getClient(clientName);
		
		if(client == null) { //rank1
			turnOnError(String.format("Error: To-Account %s does not exist", clientName));
		}
		else if(amount <= 0) { //rank 2
			turnOnError("Error: Non-Positive Amount");
		}
		else {

			
			
			savedTransactionEntity = Optional.of(clientService.deposit(client.getId(), amount));
			
			turnOffError();
		}
		
		
		return savedTransactionEntity;
		
	}
	
	
	
	
	public Optional<List<TransactionEntity>> transfer(String fromName, String toName, double amount) {

		
		Optional<List<TransactionEntity>> optionalSavedTransactionEntities = Optional.empty();
		
		
		ClientEntity fromClient = clientService.getClient(fromName);
		ClientEntity toClient = clientService.getClient(toName);
		
		if(fromClient == null) {
			turnOnError(String.format("Error: From-Account %s does not exist", fromName));
		}
		else if(toClient == null) {
			turnOnError(String.format("Error: To-Account %s does not exist", toName));
		}
		else if(amount <= 0) {
			turnOnError("Error: Non-Positive Amount");
		}
		else if(amount > fromClient.getBalance()) {
			turnOnError("Error: Amount too large to transfer");

		}
		else {
			List<TransactionEntity> savedTransactionEntities = new ArrayList<>();
			
			savedTransactionEntities.add(clientService.withdraw(fromClient.getId(), amount));
			savedTransactionEntities.add(clientService.deposit(toClient.getId(), amount));
			
			optionalSavedTransactionEntities = Optional.of(savedTransactionEntities);
			
			turnOffError();
		}
		
		
		
	
		return optionalSavedTransactionEntities;

		
	}
	
	
	
	
	
	

	
	
	
	/* Helper Methods */
	
	
	
	/*
	 * Sets error status to true and sets its error message to given argument
	 */
	private void turnOnError(String errorStr) {
		// do a full update (excluding id) on bank
		BankEntity bankEntity = BankEntity.builder()
				.id(0L)
				.error(true)
				.errorStr(errorStr)
				.build();
		
		createUpdateBank(bankEntity);

	}
	
	/*
	 * Sets error status to false and clears error message
	 */
	private void turnOffError() {
		// do a full update (excluding id) on bank
		BankEntity bankEntity = BankEntity.builder()
				.id(0L)
				.error(false)
				.errorStr("")
				.build();
		
		createUpdateBank(bankEntity);
		
	
		
	}
	
	

	

	


	
	
	
	
	
	
	

	

	


}
