package com.jordantran.bank_api;



import com.jordantran.bank_api.domain.dto.BankDTO;
import com.jordantran.bank_api.domain.dto.ClientDTO;
import com.jordantran.bank_api.domain.dto.TransactionDTO;



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
				.id(null) // auto generated, if the id field is provided, Hibernate assumes the entity already exists in the database. If no matching row is found, Hibernate interprets this as the entity having been deleted by another transaction, resulting in an OptimisticLockException
				.name("John")
				.balance(20.3)
//				.numOfTransactions(0L)
				.bankDTO(bankDTO)
				.build();
	}
	
	public static ClientDTO createClientA() {
		return createClientA(null);
	}

	public static TransactionDTO createTransactionDepositA(ClientDTO clientDTO) {
		return TransactionDTO.builder()
				.id(null) // auto generated
				.transactionType("DEPOSIT")
				.amount(10.2)
				.clientDTO(clientDTO)
				.build();
	}
}
