package com.paxos.test.transac.api;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.paxos.test.transac.dto.TransactionRequest;
import com.paxos.test.transac.enums.MoneyTransactionType;
import com.paxos.test.transac.model.Account;
import com.paxos.test.transac.model.CreditTransaction;
import com.paxos.test.transac.model.DebitTransaction;
import com.paxos.test.transac.model.MoneyTransaction;
import com.paxos.test.transac.repository.AccountRepository;
import com.paxos.test.transac.repository.MoneyTransactionRepository;
import com.sun.istack.NotNull;

@RestController
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MoneyTransactionRepository txRepository;

	private final Log LOG = LogFactory.getLog(AccountController.class);

	private final long UNIQUE_USER_ID = 1L;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	@RequestMapping(method = RequestMethod.GET, path = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Account> getCurrentAccountBalance() {
		Account account = accountRepository.findById(UNIQUE_USER_ID).get();
		return ResponseEntity.ok().body(account);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
	@RequestMapping(method = RequestMethod.GET, path = "/transactions", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<MoneyTransaction>> getHistorical() {
		List<MoneyTransaction> txList = txRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		return ResponseEntity.ok(txList);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
	@RequestMapping(method = RequestMethod.GET, path = "/transactions/{transactionId}", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MoneyTransaction> getTransactionById(@PathVariable @NotNull long transactionId) {
			Optional<MoneyTransaction>  op = txRepository.findById(transactionId);
			if(op.isPresent()) {
				return ResponseEntity.ok(op.get());
			} else {
				return ResponseEntity.notFound().build();
			}

	}

	private MoneyTransaction map(TransactionRequest request) {
		if (MoneyTransactionType.credit.equals(request.getType())) {
			return new CreditTransaction(request.getAmount());
		}
		if (MoneyTransactionType.debit.equals(request.getType())) {
			return new DebitTransaction(request.getAmount());
		} else {
			throw new InvalidParameterException();
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.SERIALIZABLE, rollbackFor = {
			RuntimeException.class, InvalidTransactionException.class })
	@RequestMapping(method = RequestMethod.POST, path = "/transactions", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> commitNewTransaction(@RequestBody @Valid @NotNull TransactionRequest transactionRequest)
			throws InvalidTransactionException {
		try {
			MoneyTransaction transactionEntity = map(transactionRequest);
			Account userAccount = accountRepository.getOne(UNIQUE_USER_ID);
			userAccount.updateBalance(transactionEntity);
			accountRepository.saveAndFlush(userAccount);
			MoneyTransaction createdTransaction = txRepository.saveAndFlush(transactionEntity);
			return ResponseEntity.ok(createdTransaction);
		} catch (ConstraintViolationException | DataIntegrityViolationException e) {
			LOG.warn(e);
			throw new InvalidTransactionException();
		}

	}

}
