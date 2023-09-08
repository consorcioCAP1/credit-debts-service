package com.nttdata.bootcamp.creditdebtsservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nttdata.bootcamp.creditdebtsservice.documents.CreditDebt;
import com.nttdata.bootcamp.creditdebtsservice.dto.CreditdebtDto;
import com.nttdata.bootcamp.creditdebtsservice.service.CreditDebtService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class CreditDebtController {

	@Autowired
	private CreditDebtService creditDebtService;

	@PostMapping("/createDebtsAccount")
    public   Flux<CreditDebt> createCreditDebt(@RequestBody CreditdebtDto creditdebtDto) {
		return creditDebtService.saveCreditDebt(creditdebtDto);
	}

    @PutMapping("/updateCreditDebt")
    public Mono<ResponseEntity<Object>> updateCreditDebt(@RequestBody CreditdebtDto creditdebtDto) {
    	System.out.println("escribindo este mensaje para practicar cambios");
    	return creditDebtService.updateCreditDebt(creditdebtDto).flatMap(objResponse -> {
            ResponseEntity<Object> responseEntity2 = ResponseEntity.ok(objResponse);
            return Mono.just(responseEntity2);
        })
        .onErrorResume(error -> {
            ResponseEntity<Object> responseEntity = ResponseEntity
            		.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
            return Mono.just(responseEntity);
        });
    	
    }

    @PostMapping("/findDebtsByBankAccountNumberIn")
    public Mono<Boolean> findDebtsByBankAccountNumberIn(@RequestBody List<String> bankAccountNumbers){
    	return creditDebtService.findDebtsByBankAccountNumberIn(bankAccountNumbers).hasElements();
    }
}
