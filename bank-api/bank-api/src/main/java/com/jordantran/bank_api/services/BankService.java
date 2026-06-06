package com.jordantran.bank_api.services;

import org.springframework.stereotype.Service;

import com.jordantran.bank_api.repositories.BankRepository;

@Service
public class BankService {

	
	BankRepository bankRepository;
	
	public BankService(BankRepository bankRepository) {
		this.bankRepository = bankRepository;
	}
	
	
	public long count() {
		return bankRepository.count();
	}


}
