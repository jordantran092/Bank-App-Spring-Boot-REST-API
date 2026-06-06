package com.jordantran.bank_api.services;

import org.springframework.stereotype.Service;

import com.jordantran.bank_api.model.BankEntity;
import com.jordantran.bank_api.repositories.BankRepository;

@Service
public class BankService {

	
	BankRepository bankRepository;
	
	public BankService(BankRepository bankRepository) {
		this.bankRepository = bankRepository;
	}
	
	public BankEntity save(BankEntity bankEntity) {
		return bankRepository.save(bankEntity);
	}
	
	
	public long count() {
		return bankRepository.count();
	}


}
