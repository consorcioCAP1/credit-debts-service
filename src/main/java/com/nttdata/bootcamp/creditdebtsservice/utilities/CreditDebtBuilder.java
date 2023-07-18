package com.nttdata.bootcamp.creditdebtsservice.utilities;

import com.nttdata.bootcamp.creditdebtsservice.documents.CreditDebt;
import com.nttdata.bootcamp.creditdebtsservice.dto.CreditdebtDto;

public class CreditDebtBuilder {

	private CreditDebtBuilder(){}
	public static CreditDebt mapToCreditDtoToCreditDebt(CreditdebtDto creditDebt) {
        return CreditDebt.builder()
                .id(creditDebt.getId())
                .paymentAmount(creditDebt.getPaymentAmount())
                .bankFeeNumber(creditDebt.getBankFeeNumber())
                .bankAccountNumber(creditDebt.getBankAccountNumber())
                .outStandingBankFee(creditDebt.getOutStandingBankFee())
                .build();
    }


}
