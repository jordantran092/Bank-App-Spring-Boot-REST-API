package com.jordantran.bank_api.services;



import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jordantran.bank_api.domain.entities.*;
import com.jordantran.bank_api.repositories.TransactionRepository;


@Service
public class TransactionService {

	
	private TransactionRepository transactionRepository;
	
	
	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	
	public TransactionEntity save(TransactionEntity transactionEntity) {
		return transactionRepository.save(transactionEntity);
		
	}
	
	
    /*
     * Appends a transaction entity to the transactions table based on type and amount
     */
	public TransactionEntity createTransaction(String transactionType, double amount, ClientEntity clientEntity) {
		TransactionEntity transaction = TransactionEntity.builder()
				.id(null)
				.transactionType(transactionType)
				.amount(amount)
				.clientEntity(clientEntity)
				.build();
		
		return save(transaction);
	}

	public List<TransactionEntity> findAll() {
		return transactionRepository.findAll();
	}
	
	public Optional<TransactionEntity> findById(Long id) {
		return transactionRepository.findById(id);
	}
	
	
    /*
     * Returns status of a transaction in terms of transaction type and amount
     */
    public String getStatus(Long id) {
    	
    	Optional<TransactionEntity> optionalTransactionEntity = findById(id);
    	
    	if(optionalTransactionEntity.isPresent()) {
    		TransactionEntity transaction = optionalTransactionEntity.get();
    		return String.format("Transaction %s: $%.2f", transaction.getTransactionType(), transaction.getAmount());
    	}
    	else {
    		throw new RuntimeException("Transaction does not exist");
    	}
    	
    }


	
	
	
	
	
}
