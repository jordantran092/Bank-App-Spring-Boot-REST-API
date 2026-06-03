package com.jordantran.bank_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@GetMapping(path = "/hello")
	public String helloWorld() {
		return "Hello world";
	}
}
