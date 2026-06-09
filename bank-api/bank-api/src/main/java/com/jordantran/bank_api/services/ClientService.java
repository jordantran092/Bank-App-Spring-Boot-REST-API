package com.jordantran.bank_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jordantran.bank_api.domain.entities.ClientEntity;
import com.jordantran.bank_api.domain.entities.TransactionEntity;
import com.jordantran.bank_api.repositories.ClientRepository;


@Service
public class ClientService {

	
	private ClientRepository clientRepository;
	
	
	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
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

	public TransactionEntity deposit(Long id, double amount) {
		// TODO Auto-generated method stub
		
	}
}
