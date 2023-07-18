package com.nttdata.bootcamp.creditdebtsservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankTransactionDto {
	private String id;
	private Double amount;
	private String type;
	private String date;
	private String description;
	private String bankAccountNumber;
}
