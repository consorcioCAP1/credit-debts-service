package com.nttdata.bootcamp.creditdebtsservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.nttdata.bootcamp.creditdebtsservice.documents.CreditDebt;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditDebtRepository extends ReactiveMongoRepository<CreditDebt, String>{

	 @Query("{'bankFeeNumber': ?0, 'bankAccountNumber': ?1, 'outStandingBankFee': ?2}")
	 Mono<CreditDebt> findByBankFeeNumberAndBankAccountNumberAndOutStandingBankFee(int bankFeeNumber,
			 	String bankAccountNumber, String outStandingBankFee);
	 
	 @Query("{'bankAccountNumber': {$in: ?0}, 'outStandingBankFee': 'YES', "
	 			+ "$where: 'Date.parse(this.paymentDate) < Date.now()'}")
	 Flux<CreditDebt> findByBankAccountNumberInAndOutStandingBankFeeAndPaymentDateBefore(
			 	List<String> bankAccountNumbers);

}
