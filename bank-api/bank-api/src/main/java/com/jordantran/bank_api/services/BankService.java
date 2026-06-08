package com.jordantran.bank_api.services;

import org.springframework.stereotype.Service;

import com.jordantran.bank_api.repositories.BankRepository;

import domain.entities.*;

@Service
public class BankService {
	
	private final long MAX_CLIENTS = 6; // 6 for demo error handling purposes

	
	private BankRepository bankRepository;
	private ClientService clientService;
	
	public BankService(BankRepository bankRepository, ClientService clientService) {
		this.bankRepository = bankRepository;
		this.clientService = clientService;
	}
	
	public BankEntity save(BankEntity bankEntity) {
		return bankRepository.save(bankEntity);
	}
	
	
	public long count() {
		return bankRepository.count();
	}

	public ClientEntity addClient(ClientEntity clientEntity) {

		ClientEntity savedClientEntity = null;
		
		String name = clientEntity.getName();
		double amount = clientEntity.getBalance();
		
		
		if(clientService.count() == this.MAX_CLIENTS) { //rank1
			turnOnError("Error: Maximum Number of Accounts Reached");
		}
		else if(getClient(name) != null) { //rank 2
			turnOnError(String.format("Error: Client %s already exists", name));
		}
		else if(amount <= 0) { //rank 3
			turnOnError("Error: Non-Positive Initial Balance");
		}
		else {
			savedClientEntity = clientService.save(clientEntity);
			
			turnOffError();
		}
		
		
		// return
		return savedClientEntity;
		
		
		
		
		/*
		
				
		
		if(this.noc == this.MAX_CLIENTS) { //rank1
			turnOnError("Error: Maximum Number of Accounts Reached");
		}
		else if(getClient(name) != null) { //rank 2
			turnOnError(String.format("Error: Client %s already exists", name));
		}
		else if(amount <= 0) { //rank 3
			turnOnError("Error: Non-Positive Initial Balance");
		}
		else {
			this.clients[this.noc] = new Client(name, amount);
			this.noc++;
			
			turnOffError();
		}
		
		
		 */
	}


}
