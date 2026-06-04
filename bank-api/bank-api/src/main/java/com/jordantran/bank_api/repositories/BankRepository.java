package com.jordantran.bank_api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jordantran.bank_api.model.BankEntity;

@Repository
public interface BankRepository extends CrudRepository<BankEntity, Long> {

}
