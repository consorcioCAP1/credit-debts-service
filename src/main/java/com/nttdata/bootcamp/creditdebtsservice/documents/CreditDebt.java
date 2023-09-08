package com.nttdata.bootcamp.creditdebtsservice.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Document(collection = "creditdebts")
@AllArgsConstructor
@Data
@Builder
public class CreditDebt {

	@Id
	private String id;
	private Double paymentAmount;
	private int bankFeeNumber;
	private String outStandingBankFee;
	private String bankAccountNumber;
	private String paymentDate;
	private String cancelDate;
	
}
