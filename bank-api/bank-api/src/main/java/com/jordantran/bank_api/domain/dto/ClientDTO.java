package com.jordantran.bank_api.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getters and setters 
@AllArgsConstructor // constructor with all attributes
@NoArgsConstructor // constructor with no attributes
@Builder // instantiate objects easier

public class ClientDTO {
	
	private Long id;
	

    private String name;
	
	
    private double balance;

	
	
//    private Long numOfTransactions; // number of transactions
    

    private BankDTO bankDTO; 


	
}





