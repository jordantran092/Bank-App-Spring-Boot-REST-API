package com.jordantran.bank_api.components.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jordantran.bank_api.components.mappers.Mapper;
import com.jordantran.bank_api.domain.dto.TransactionDTO;
import com.jordantran.bank_api.domain.entities.TransactionEntity;

/*
 * Impl uses ModelMapper to convert entities to DTOs and back
 */

@Component
public class TransactionMapperImpl implements Mapper<TransactionEntity, TransactionDTO> {
	
	private ModelMapper modelMapper;
	
	public TransactionMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public TransactionDTO mapTo(TransactionEntity entity) {
		return modelMapper.map(entity, TransactionDTO.class);
	}

	@Override
	public TransactionEntity mapFrom(TransactionDTO dto) {
		return modelMapper.map(dto, TransactionEntity.class);
	}

}
