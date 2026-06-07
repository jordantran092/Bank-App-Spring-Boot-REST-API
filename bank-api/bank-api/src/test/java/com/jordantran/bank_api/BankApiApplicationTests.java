package com.jordantran.bank_api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.jordantran.bank_api.services.BankService;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BankApiApplicationTests {
	
	private BankService bankService;
	
	@Autowired
	public BankApiApplicationTests(BankService bankService) {
		this.bankService = bankService;
	}

	
	
	@Test
	void testThatBankIsIntialized() {
		// check if exactly only 1 bank record exists, contents don't matter
		
		assertEquals(this.bankService.count(), 1);
		
	}
	
	

}
