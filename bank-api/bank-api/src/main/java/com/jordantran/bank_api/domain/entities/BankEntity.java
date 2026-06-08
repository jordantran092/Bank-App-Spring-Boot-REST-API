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
@Table(name = "bank")
public class BankEntity {
	
	
	@Id @NotNull
	private Long id;
	
	@NotNull
	private boolean error;
	
	@NotNull
	private String errorStr;
	
//	@NotNull @Min(0)
//	private Long numOfClients; // number of clients
	

}


