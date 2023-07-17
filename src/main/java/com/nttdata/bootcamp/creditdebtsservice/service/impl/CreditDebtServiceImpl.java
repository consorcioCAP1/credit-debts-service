package com.nttdata.bootcamp.creditdebtsservice.service.impl;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nttdata.bootcamp.creditdebtsservice.documents.CreditDebt;
import com.nttdata.bootcamp.creditdebtsservice.dto.CreditdebtDto;
import com.nttdata.bootcamp.creditdebtsservice.repository.CreditDebtRepository;
import com.nttdata.bootcamp.creditdebtsservice.service.CreditDebtService;
import reactor.core.publisher.Flux;

@Service
public class CreditDebtServiceImpl implements CreditDebtService{

	@Autowired
	CreditDebtRepository repository;
	
	public final String PENDING_PAYMENT_YES = "YES";
	public final String PENDING_PAYMENT_NO = "NO";

	@Override
	public Flux<CreditDebt> saveCreditDebt(CreditdebtDto creditDebt){
		//creando variable para trabajar con la fecha inicio de pago
	    LocalDate paymentStartDate = LocalDate.parse(creditDebt.getPaymentStartDate());
	    //retornamos el flux 
	    Flux<CreditDebt> savedDebts = Flux.range(0, creditDebt.getNumberBankPaymentInstallments())
	            .flatMap(i -> {
	            	//nos basamos en registrar las cuotas mensuales
	            	LocalDate paymentDate = paymentStartDate.plusMonths(i);
	            	//construimos el CreditDebt 
	            	CreditDebt creaditDebtDocument = CreditDebt.builder()
	            			.paymentAmount(creditDebt.getPaymentAmount())
							.bankFeeNumber(i)
							.outStandingBankFee(PENDING_PAYMENT_YES)
							.bankAccountNumber(creditDebt.getBankAccountNumber()+1)
							.paymentDate(paymentDate.toString())
	                        .build();
	                return repository.save(creaditDebtDocument);
            });

	    return savedDebts;
	}
	


	
}
