package com.jordantran.bank_api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jordantran.bank_api.domain.entities.BankEntity;

@Repository
public interface BankRepository extends JpaRepository<BankEntity, Long> {

}
