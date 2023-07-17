package com.nttdata.bootcamp.creditdebtsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nttdata.bootcamp.creditdebtsservice.documents.CreditDebt;
import com.nttdata.bootcamp.creditdebtsservice.dto.CreditdebtDto;
import com.nttdata.bootcamp.creditdebtsservice.service.CreditDebtService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/")
public class CustomerCreditAccountController {

	@Autowired
	private CreditDebtService creditDebtService;

	@PostMapping("/createDebtsAccount")
    public   Flux<CreditDebt> createCreditDebt(@RequestBody CreditdebtDto creditdebtDto) {
		return creditDebtService.saveCreditDebt(creditdebtDto);
	}
}
