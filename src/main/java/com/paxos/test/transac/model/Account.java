package com.paxos.test.transac.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Account implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4122249475983566973L;

	@Id 
	@Column(nullable = false,updatable = false)
	private Long id = 1L;
	
	@NotNull
	@PositiveOrZero
	@Column(nullable = false)
	private BigDecimal balance;

	public Account() {
		this.balance = BigDecimal.ZERO;
	}	
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public void updateBalance(MoneyTransaction moneyTransaction) {
		switch(moneyTransaction.getType()) {
			case debit: 
				balance = balance.subtract(moneyTransaction.getAmount()); break;
			case credit: 
				balance = balance.add(moneyTransaction.getAmount()); break;
		}
	}

}
