package com.jordantran.bank_api;



import com.jordantran.bank_api.domain.dto.BankDTO;
import com.jordantran.bank_api.domain.dto.ClientDTO;
import com.jordantran.bank_api.domain.dto.TransactionDTO;
import com.jordantran.bank_api.domain.dto.TransactionDepositWithdrawDTO;
import com.jordantran.bank_api.domain.dto.TransactionTransferDTO;



public class TestDataUtil {
	
	public static BankDTO createBank_DTO() {
		return BankDTO.builder()
				.id(0L)
				.error(false)
				.errorStr("")
//				.numOfClients(0L)
				.build();
	}
	
	public static ClientDTO createClientA_DTO(BankDTO bankDTO) {
		return ClientDTO.builder()
				.id(null) // auto generated, if the id field is provided, Hibernate assumes the entity already exists in the database. If no matching row is found, Hibernate interprets this as the entity having been deleted by another transaction, resulting in an OptimisticLockException
				.name("John")
				.balance(20.3)
//				.numOfTransactions(0L)
				.bankDTO(bankDTO)
				.build();
	}
	
	public static ClientDTO createClientB_DTO() {
		return ClientDTO.builder()
				.id(null) // auto generated, if the id field is provided, Hibernate assumes the entity already exists in the database. If no matching row is found, Hibernate interprets this as the entity having been deleted by another transaction, resulting in an OptimisticLockException
				.name("Bob")
				.balance(5)
				.bankDTO(null)
				.build();
	}
	
	public static ClientDTO createClientA_DTO() {
		return createClientA_DTO(null);
	}

	public static TransactionDepositWithdrawDTO createTransactionDepositA_DTO() {
		return TransactionDepositWithdrawDTO.builder()
				.transactionType("DEPOSIT")
				.amount(10.2)
				.name("John")
				.build();
	}
	
	public static TransactionDepositWithdrawDTO createTransactionWithdrawA_DTO() {
		return TransactionDepositWithdrawDTO.builder()
				.transactionType("WITHDRAW")
				.amount(5)
				.name("John")
				.build();
	}

	public static TransactionTransferDTO createTransactionTransferA_DTO() {
		return TransactionTransferDTO.builder()
				.amount(5)
				.fromName("John")
				.toName("Bob")
				.build();
	}
}
