package com.jordantran.bank_api.services;



import org.springframework.stereotype.Service;

import com.jordantran.bank_api.domain.entities.*;
import com.jordantran.bank_api.repositories.TransactionRepository;


@Service
public class TransactionService {

	
	private TransactionRepository transactionRepository;
	
	
	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
//	public long count() {
//		return clientRepository.count();
//	}
	
	public TransactionEntity save(TransactionEntity transactionEntity) {
		return transactionRepository.save(transactionEntity);
		
	}
	
	public TransactionEntity createTransaction(String transactionType, double amount, ClientEntity clientEntity) {
		TransactionEntity transaction = TransactionEntity.builder()
				.id(null)
				.transactionType(transactionType)
				.amount(amount)
				.clientEntity(clientEntity)
				.build();
		
		return save(transaction);
	}
	
	
//	public List<ClientEntity> findAll() {
//		return clientRepository.findAll();
//	}
	
//	public Optional<ClientEntity> findById(Long id) {
//		return clientRepository.findById(id);
//	}


	
	
	
	
	
	
	
	
}
