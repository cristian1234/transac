package com.paxos.test.transac.dto;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.paxos.test.transac.enums.MoneyTransactionType;

@Valid
public class TransactionRequest {

	@Positive
	@NotNull
	private BigDecimal amount;
	
	@NotNull
	private MoneyTransactionType type;
	
	
	public BigDecimal getAmount() {
		return amount;
	}

	public MoneyTransactionType getType() {
		return type;
	}

}
