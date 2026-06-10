package com.jordantran.bank_api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jordantran.bank_api.domain.entities.ClientEntity;
import com.jordantran.bank_api.domain.entities.TransactionEntity;
import com.jordantran.bank_api.repositories.ClientRepository;


@Service
public class ClientService {

	
	private ClientRepository clientRepository;
	private TransactionService transactionService;
	
	
	public ClientService(ClientRepository clientRepository, TransactionService transactionService) {
		this.clientRepository = clientRepository;
		this.transactionService = transactionService;
	}
	
	public long count() {
		return clientRepository.count();
	}
	
	public ClientEntity save(ClientEntity clientEntity) {
		return clientRepository.save(clientEntity);
		
	}
	
	public List<ClientEntity> findAll() {
		return clientRepository.findAll();
	}
	
	public Optional<ClientEntity> findById(Long id) {
		return clientRepository.findById(id);
	}
	
	public  ClientEntity getClient(String name) {
		ClientEntity client = null;
		
		List<ClientEntity> clients = findAll();
		
		
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

	public TransactionEntity deposit(Long id, double amount) {

		
		Optional<ClientEntity> optionalClient = findById(id);
		
		if(optionalClient.isPresent()) {
			ClientEntity client = optionalClient.get();
			
			client.setBalance(client.getBalance() + amount);
			
			return transactionService.createTransaction("DEPOSIT", amount, client);
			
		}
		else {
			// should not happen because existence validated in BankService, but for extra caution
			throw new RuntimeException("Client does not exist");
		}
		
		
	}
	
	public TransactionEntity withdraw(Long id, double amount) {

		
		Optional<ClientEntity> optionalClient = findById(id);
		
		if(optionalClient.isPresent()) {
			ClientEntity client = optionalClient.get();
			
			client.setBalance(client.getBalance() - amount);
			
			return transactionService.createTransaction("WITHDRAW", amount, client);
			
		}
		else {
			// should not happen because existence validated in BankService, but for extra caution
			throw new RuntimeException("Client does not exist");
		}
		
		
	}
	
	
	

	
	/* Helper Methods */

	
}
