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
@Table(name = "bank_transaction")
public class TransactionEntity {
	
	@Id @NotNull
	private Long id;
	
	@NotNull
    private String transactionType;
    
	@NotNull @Positive
    private double amount;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity clientEntity; 


	
}

/*




- Client
    - ON DELETE restrict default, because don’t want bank to delete if it has client records. Handle it manually
    - ON update restrict default, the PK value of bank should never really change (can cause problems like FK references breaking) so prob an accident at that point
- Transaction
    - ON update restrict default, the PK value of client should never really change (can cause problems like FK references breaking) so prob an accident at that point










 Testing 

 Expect Pass 
-- INSERT INTO bank VALUES(0, FALSE, '', 0);
-- INSERT INTO client VALUES(0, 0, 0.00, 'Jordan', 0);
-- INSERT INTO client VALUES(1, 0, 0.23, 'John', 0);
-- INSERT INTO bank_transaction VALUES(0, 'DEPOSIT', 0.00, 0);
-- INSERT INTO bank_transaction VALUES(1, 'DEPOSIT', 2.23, 0);


Expect Fail 
-- INSERT INTO bank_transaction VALUES(0, 'DEPOSIT', -2, 0);

-- SELECT * FROM bank;
-- SELECT * FROM client;
-- SELECT * FROM bank_transaction;

*/







