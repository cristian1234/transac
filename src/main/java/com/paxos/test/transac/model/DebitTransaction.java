package com.paxos.test.transac.model;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.paxos.test.transac.enums.MoneyTransactionType;

@Entity
@DiscriminatorValue("debit")
public class DebitTransaction extends MoneyTransaction {

	public DebitTransaction(BigDecimal amount) {
		super(amount);
	}
	
	DebitTransaction() {
		super();
	}
	
	@Override
	public MoneyTransactionType getType() {
		return MoneyTransactionType.debit;
	}
	
}
