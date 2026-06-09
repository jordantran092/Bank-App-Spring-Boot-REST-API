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
