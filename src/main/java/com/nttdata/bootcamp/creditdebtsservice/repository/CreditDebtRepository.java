package com.nttdata.bootcamp.creditdebtsservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.creditdebtsservice.documents.CreditDebt;

public interface CreditDebtRepository extends ReactiveMongoRepository<CreditDebt, String>{

}
