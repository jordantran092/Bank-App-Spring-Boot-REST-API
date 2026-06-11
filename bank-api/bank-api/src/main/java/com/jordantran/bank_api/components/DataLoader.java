package com.jordantran.bank_api.components;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jordantran.bank_api.domain.entities.BankEntity;
import com.jordantran.bank_api.services.BankService;



@Component
public class DataLoader implements CommandLineRunner { // a way to run start up code
	
	private BankService bankService;
	
	public DataLoader(BankService bankService) {
		this.bankService = bankService;
	}
	
	@Override
	public void run(String... args) {
		
		// if no bank exists i.e. first time database is created, then make only one
		if(bankService.count() < 1) {
			BankEntity bankEntity = BankEntity.builder()
					.id(0L)
					.error(false)
					.errorStr("")
					.build();
			
			
			bankService.createUpdateBank(bankEntity);
			
			
		}
		

	}
}
