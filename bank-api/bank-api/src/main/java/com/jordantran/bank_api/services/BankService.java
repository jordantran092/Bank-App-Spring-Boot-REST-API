package com.jordantran.bank_api.services;

import java.util.List;

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

		ClientEntity savedClientEntity = null;
		
		String name = clientEntity.getName();
		double amount = clientEntity.getBalance();
		
		
		/*
		 * higher prio is higher in the if chain, then just do else if.... as you go down, to create the priority 
		 * 
		 */
		if(getClient(name) != null) { //rank 1
			turnOnError(String.format("Error: Client %s already exists", name));
		}
		else if(amount <= 0) { //rank 2
			turnOnError("Error: Non-Positive Initial Balance");
		}
		else {
			
			clientEntity.setNumOfTransactions(0L);
			clientEntity.setBankEntity(bankRepository.findById(0L).get()); // value in the optional bank object
			
			savedClientEntity = clientService.save(clientEntity);
			
			turnOffError();
		}
		
		
		// return
		return savedClientEntity;
		
		
		
		
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
	

	
	public ClientEntity getClient(String name) {
		ClientEntity client = null;
		
		List<ClientEntity> clients = clientService.findAll();
		
		
		boolean foundClient = false;
		for(int i = 0; !foundClient && i < clients.size(); i++) {
			client = clients.get(i);
			foundClient = client.getName().equals(name);	
		}
		
		//if not found account will return null, otherwise will mean account was found successfully and will be returned eventually
		if(!foundClient) { 
			client = null;
		}
		
		return client;
	}

	public TransactionEntity deposit(TransactionEntity transactionEntity) {
		/*
		 
		Client client = getClient(name);
		
		if(client == null) { //rank1
			turnOnError(String.format("Error: To-Account %s does not exist", name));
		}
		else if(amount <= 0) { //rank 2
			turnOnError("Error: Non-Positive Amount");
		}
		else {
			client.deposit(amount);
			
			turnOffError();
		}
		 */
		
		TransactionEntity savedTransactionEntity = null;
		
		String clientName = transactionEntity.getClientEntity().getName();
		double amount = transactionEntity.getAmount();
		
		ClientEntity client = getClient(clientName);
		
		if(client == null) { //rank1
			turnOnError(String.format("Error: To-Account %s does not exist", clientName));
		}
		else if(amount <= 0) { //rank 2
			turnOnError("Error: Non-Positive Amount");
		}
		else {
			
			
			savedTransactionEntity = clientService.deposit(client.getId(), amount);
			
			turnOffError();
		}
		
		
		return savedTransactionEntity;
		
	}
	
	


}
