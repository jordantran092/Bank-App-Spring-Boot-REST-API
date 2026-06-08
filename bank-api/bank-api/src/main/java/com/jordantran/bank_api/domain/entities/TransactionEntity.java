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
@Table(name = "bank_transaction")
public class TransactionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq") // SEQUENCE for a sequence of values x, x+1, ...
	private Long id;
	
	@NotNull
    private String transactionType;
    
	@NotNull @Min(0)
    private double amount;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity clientEntity; 


	
}
