package com.paxos.test.transac.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;

import com.paxos.test.transac.enums.MoneyTransactionType;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MoneyTransaction implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2835363673466179815L;

	@Id
	@GeneratedValue
	@Column(updatable = false,nullable = false)
	private Long id;
	
	@NotNull
	@Column(updatable = false,nullable = false)
	private BigDecimal amount;

	@CreatedDate
	@Column(updatable = false)
	private Instant effectiveDate;
	
	MoneyTransaction() {}
	
	public MoneyTransaction(BigDecimal amount) {
		this.amount = amount;
		this.effectiveDate = Instant.now();
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public Long getId() {
		return id;
	}
	
	public Instant getEffectiveDate() {
		return effectiveDate;
	}
	
	public abstract MoneyTransactionType getType();
}
