package com.nttdata.bootcamp.creditdebtsservice.service;

import java.util.List;

import com.nttdata.bootcamp.creditdebtsservice.documents.CreditDebt;
import com.nttdata.bootcamp.creditdebtsservice.dto.CreditdebtDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditDebtService {

	public Flux<CreditDebt> saveCreditDebt(CreditdebtDto creditDebt);
	public Mono<CreditDebt> updateCreditDebt(CreditdebtDto creditdebtDto);
	public Flux<CreditDebt> findByBankAccountNumberIn(List<String> bankAccountNumbers);
}
