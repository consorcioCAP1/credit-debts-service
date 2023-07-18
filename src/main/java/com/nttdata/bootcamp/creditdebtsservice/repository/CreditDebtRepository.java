package com.nttdata.bootcamp.creditdebtsservice.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.nttdata.bootcamp.creditdebtsservice.documents.CreditDebt;
import reactor.core.publisher.Mono;

public interface CreditDebtRepository extends ReactiveMongoRepository<CreditDebt, String>{

	 @Query("{'bankFeeNumber': ?0, 'bankAccountNumber': ?1, 'outStandingBankFee': ?2}")
	 Mono<CreditDebt> findByBankFeeNumberAndBankAccountNumberAndOutStandingBankFee(int bankFeeNumber,
			 	String bankAccountNumber, String outStandingBankFee);

}
