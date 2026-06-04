package com.jordantran.bank_api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jordantran.bank_api.model.TransactionEntity;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

}
