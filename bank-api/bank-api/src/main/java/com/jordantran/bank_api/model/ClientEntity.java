package com.jordantran.bank_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getters and setters 
@AllArgsConstructor // constructor with all attributes
@NoArgsConstructor // constructor with no attributes
@Builder // instantiate objects easier
@Entity
@Table(name = "client")
public class ClientEntity {
	
	@Id @NotNull
	private Long id;
	
	@NotNull
    private String name;
	
	@NotNull @Min(0)
    private double balance;

	
	@NotNull @Min(0)
    private Long numOfTransactions; // number of transactions
    
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private BankEntity bankEntity; 


	
}





