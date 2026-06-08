package com.jordantran.bank_api.components.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jordantran.bank_api.components.mappers.Mapper;
import com.jordantran.bank_api.domain.dto.ClientDTO;
import com.jordantran.bank_api.domain.entities.ClientEntity;

/*
 * Impl uses ModelMapper to convert entities to DTOs and back
 */

@Component
public class ClientMapperImpl implements Mapper<ClientEntity, ClientDTO> {
	
	private ModelMapper modelMapper;
	
	public ClientMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public ClientDTO mapTo(ClientEntity entity) {
		return modelMapper.map(entity, ClientDTO.class);
	}

	@Override
	public ClientEntity mapFrom(ClientDTO dto) {
		return modelMapper.map(dto, ClientEntity.class);
	}

}
