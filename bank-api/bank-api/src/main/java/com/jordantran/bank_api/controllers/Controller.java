package com.jordantran.bank_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.jordantran.bank_api.components.mappers.Mapper;

import domain.dto.*;
import domain.entities.*;

@RestController
public class Controller {
	
	Mapper<ClientEntity, ClientDTO> clientMapper;
	
	public Controller(Mapper<ClientEntity, ClientDTO> clientMapper) {
		this.clientMapper = clientMapper;
	}

	@PostMapping(path = "/api/v1/bank")
	public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO clientDTO) {
		/*
		 * bank.addclient will handle the business logic, not here
		 * 
		 * convert dto to entity
		 * input into bank.addclient 
		 * return result of added client 
		 * convert back to dto
		 * return dto
		 */
		
		
		ClientEntity clientEntity = clientMapper.mapFrom(clientDTO); 
		
		
		// return
	}
}
