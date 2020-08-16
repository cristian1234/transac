package com.paxos.test.transac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paxos.test.transac.model.Account;
import com.paxos.test.transac.repository.AccountRepository;

@SpringBootApplication
public class TransacApplication implements ApplicationRunner {

	@Autowired
	private AccountRepository ar;
	
	public static void main(String[] args) {
		SpringApplication.run(TransacApplication.class, args);
	}
	
    @Override
    public void run(ApplicationArguments args) throws Exception {
    	//Only one account created on startup
    	ar.saveAndFlush(new Account());
    }

}
