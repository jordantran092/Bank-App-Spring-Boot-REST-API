package com.jordantran.bank_api.domain.entities;

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
	
	@Id
	/*
	 * SEQUENCE for a sequence of values x, x+1, ...
	 * The id generated for the database will overwrite any other id that existed when this entity was created, with .save
	 */
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_id_seq") 
	private Long id;
	
	@NotNull
    private String name;
	
	@NotNull @Min(0)
    private double balance;

	
//	@NotNull @Min(0)
//    private Long numOfTransactions; // number of transactions
    
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private BankEntity bankEntity;




	
}





