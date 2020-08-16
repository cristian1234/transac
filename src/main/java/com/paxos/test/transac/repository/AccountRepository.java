package com.paxos.test.transac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paxos.test.transac.model.Account;

public interface AccountRepository extends JpaRepository<Account,Long >{

}
