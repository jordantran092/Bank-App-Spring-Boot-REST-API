package com.jordantran.bank_api;



import com.jordantran.bank_api.domain.dto.BankDTO;
import com.jordantran.bank_api.domain.dto.ClientDTO;



public class TestDataUtil {
	
	public static BankDTO createBank() {
		return BankDTO.builder()
				.id(0L)
				.error(false)
				.errorStr("")
//				.numOfClients(0L)
				.build();
	}
	
	public static ClientDTO createClientA(BankDTO bankDTO) {
		return ClientDTO.builder()
				.id(0L)
				.name("John")
				.balance(20.3)
				.numOfTransactions(0L)
				.bankDTO(bankDTO)
				.build();
	}
}
