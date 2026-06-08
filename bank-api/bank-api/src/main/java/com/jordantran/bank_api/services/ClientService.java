package com.jordantran.bank_api.services;

import org.springframework.stereotype.Service;

import com.jordantran.bank_api.repositories.ClientRepository;

import domain.entities.ClientEntity;


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
}
