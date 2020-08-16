package com.paxos.test.transac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paxos.test.transac.model.MoneyTransaction;


public interface MoneyTransactionRepository extends JpaRepository<MoneyTransaction,Long >{

}
