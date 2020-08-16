package com.paxos.test.transac.model;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.paxos.test.transac.enums.MoneyTransactionType;

@Entity
@DiscriminatorValue("credit")
public class CreditTransaction extends MoneyTransaction {

	CreditTransaction() {
		super();
	}
	
	public CreditTransaction(BigDecimal amount) {
		super(amount);
	}
	
	@Override
	public MoneyTransactionType getType() {
		return MoneyTransactionType.credit;
	}

}
