package com.nttdata.bootcamp.creditdebtsservice.service;

import com.nttdata.bootcamp.creditdebtsservice.documents.CreditDebt;
import com.nttdata.bootcamp.creditdebtsservice.dto.CreditdebtDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditDebtService {

	public Flux<CreditDebt> saveCreditDebt(CreditdebtDto creditDebt);

}
