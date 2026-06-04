package com.jordantran.bank_api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jordantran.bank_api.model.ClientEntity;

@Repository
public interface ClientRepository extends CrudRepository<ClientEntity, Long> {

}
