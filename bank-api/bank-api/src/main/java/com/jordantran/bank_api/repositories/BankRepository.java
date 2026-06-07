package com.jordantran.bank_api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.entities.BankEntity;

@Repository
public interface BankRepository extends CrudRepository<BankEntity, Long> {

}
