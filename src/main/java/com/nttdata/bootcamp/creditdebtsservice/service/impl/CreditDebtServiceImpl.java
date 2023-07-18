package com.nttdata.bootcamp.creditdebtsservice.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.bootcamp.creditdebtsservice.documents.CreditDebt;
import com.nttdata.bootcamp.creditdebtsservice.dto.BankTransactionDto;
import com.nttdata.bootcamp.creditdebtsservice.dto.CreditdebtDto;
import com.nttdata.bootcamp.creditdebtsservice.repository.CreditDebtRepository;
import com.nttdata.bootcamp.creditdebtsservice.service.CreditDebtService;
import com.nttdata.bootcamp.creditdebtsservice.utilities.ConvertJson;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CreditDebtServiceImpl implements CreditDebtService{

	@Autowired
	CreditDebtRepository repository;
	
	public static final String PENDING_PAYMENT_YES = "YES";
	public static final String PENDING_PAYMENT_NO = "NO";
	public static final String TRANSACTION_TYPE_BANK_CREDIT_PAYMENT = "CREDIT_PAYMENT";

	@Value("${bank-trasaction.api.url}")
    private String bankTransactionkUrl;
	
	@Value("${bank-trasaction.url.create}")
    private String bankTransactionkUrlCreate;

	@Override
	public Flux<CreditDebt> saveCreditDebt(CreditdebtDto creditDebt){
		//creando variable para trabajar con la fecha inicio de pago
	    LocalDate paymentStartDate = LocalDate.parse(creditDebt.getPaymentStartDate());
	    //retornamos el flux 
	    return Flux.range(0, creditDebt.getNumberBankPaymentInstallments())
	            .flatMap(i -> {
	            	//nos basamos en registrar las cuotas mensuales
	            	LocalDate paymentDate = paymentStartDate.plusMonths(i);
	            	//construimos el CreditDebt 
	            	CreditDebt creaditDebtDocument = CreditDebt.builder()
	            			.paymentAmount(creditDebt.getPaymentAmount())
							.bankFeeNumber(i+1)
							.outStandingBankFee(PENDING_PAYMENT_YES)
							.bankAccountNumber(creditDebt.getBankAccountNumber())
							.paymentDate(paymentDate.toString())
	                        .build();
	                return repository.save(creaditDebtDocument);
            });
	}

	//realizar pago de una cuota
    public Mono<CreditDebt> updateCreditDebt(CreditdebtDto creditdebtDto) {
        // Buscar el crédito con menor bankFeeNumber que tenga su outStandingBankFee en "YES"
        return repository
                .findByBankFeeNumberAndBankAccountNumberAndOutStandingBankFee(creditdebtDto.getBankFeeNumber(),
                		creditdebtDto.getBankAccountNumber(),PENDING_PAYMENT_YES)
                .flatMap(creditDebt -> {
                    // Actualizar el outStandingBankFee en "NO"
                	if(creditdebtDto.getPaymentAmount() < creditDebt.getPaymentAmount()) {
                		return Mono.error(new RuntimeException("Monto insuficiente para pagar la cuota."));
                	}
                	else {
                		// Establecer el valor de cancelDate con la fecha actual
                        LocalDateTime currentDate = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        creditDebt.setCancelDate(currentDate.format(formatter));
                		creditDebt.setOutStandingBankFee("NO");
                		try {
							createTransactionCreditPayment(creditdebtDto);
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
                        return repository.save(creditDebt);
                	}
                })
                .switchIfEmpty(Mono.error(new RuntimeException("No hay deudas pendientes.")));
    }

	//metodo para el consumo de la api de creacion transacion de pago deuda 
	public void createTransactionCreditPayment(CreditdebtDto creditDebt) throws JsonProcessingException{
		BankTransactionDto dto = BankTransactionDto.builder()
				.amount(creditDebt.getPaymentAmount())
				.bankAccountNumber(creditDebt.getBankAccountNumber())
				.type(TRANSACTION_TYPE_BANK_CREDIT_PAYMENT)
				.description("payment credit")
				.build();
		String objectToJson = ConvertJson.toJson(dto);
		WebClient webClient = WebClient.create(bankTransactionkUrl);
		//preguntar si devuelve info como manejarlo o para que ?
		webClient.post()
	        .uri(bankTransactionkUrlCreate)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(BodyInserters.fromValue(objectToJson))
	        .retrieve()
	        .bodyToMono(String.class)
	        .subscribe();        

	}
}
