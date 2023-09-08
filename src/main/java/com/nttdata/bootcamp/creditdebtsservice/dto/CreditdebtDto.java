package com.nttdata.bootcamp.creditdebtsservice.dto;

import lombok.Data;

@Data
public class CreditdebtDto {
	private String id;
	private Double paymentAmount;
	private int bankFeeNumber;
	private String bankAccountNumber;
	private String outStandingBankFee;
	private int numberBankPaymentInstallments;
	private String paymentStartDate;
}
