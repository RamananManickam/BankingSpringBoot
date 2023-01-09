package com.example.bankingapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bankingapp.constant.Constant;
import com.example.bankingapp.constant.ExceptionConstant;
import com.example.bankingapp.dto.AccountDto;
import com.example.bankingapp.dto.TransactionDto;
import com.example.bankingapp.entity.AccountEntity;
import com.example.bankingapp.entity.TransactionEntity;
import com.example.bankingapp.exception.BankingException;
import com.example.bankingapp.repository.AccountRepository;
import com.example.bankingapp.repository.TransactionRepository;
import com.example.bankingapp.utils.BankingUtils;

@SpringBootTest
class BankingServiceTest {

	@InjectMocks
	AccountService service;
	
	@Mock
	AccountRepository account;
	
	@Mock
	TransactionRepository trans;
	
	private AccountDto generateAccountDto() {
		AccountDto accountDto = new AccountDto();
		accountDto.setAccountBalance(2000d);
		accountDto.setAccountHolderName("mugesh");
		accountDto.setAccountNumber(22333l);
		accountDto.setAccountType("Saving");
		accountDto.setCreatedTime(null);
		accountDto.toString();
		return accountDto;
	}
	
	private TransactionDto generateTransactionDto() {
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTransactionAmount(200d);
		transactionDto.setAmountAfterTransaction(200d);
		transactionDto.setAmountBeforeTransaction(200d);
		transactionDto.setTransactionAccountHolderName("");
		transactionDto.setTransactionType("Saving");
		transactionDto.setCreatedTime(null);
		transactionDto.setTransactionAccountNumber(123344l);
		transactionDto.toString();
		return transactionDto;
	}
	
	private AccountEntity generateAccountEntity() {
		AccountEntity accountDto = new AccountEntity();
		accountDto.setAccountBalance(2000d);
		accountDto.setAccountHolderName("");
		accountDto.setAccountNumber(22333l);
		accountDto.setAccountType("Saving");
		accountDto.setCreatedTime(null);
		accountDto.setAccountId(12);
		return accountDto;
	}
	
	private TransactionEntity generateTransactionEntity() {
		TransactionEntity transactionDto = new TransactionEntity();
		transactionDto.setTransactionAmount(200d);
		transactionDto.setAmountAfterTransaction(200d);
		transactionDto.setAmountBeforeTransaction(200d);
		transactionDto.setTransactionAccountHolderName("");
		transactionDto.setTransactionType("Saving");
		transactionDto.setCreatedTime(null);
		transactionDto.setTransactionAccountNumber(123344l);
		transactionDto.setTransactionId(1233);
		transactionDto.getTransactionId();
		return transactionDto;
	}
	
	@Test
	void getAllAccountDetail() {
		List<AccountEntity> list= new ArrayList<>();
		list.add(generateAccountEntity());
		Mockito.when(account.findAll()).thenReturn(list);
		assertEquals(1,service.getAllAccountDetail().size());
	}
	
	@Test
	void getAccountDetail() throws BankingException {
		AccountEntity entity = generateAccountEntity();
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(generateAccountEntity());
		assertEquals(entity.getAccountBalance(),service.getAccountDetail(12334l).getAccountBalance());
	}
	
	@Test
	void getAccountDetailException() throws BankingException {
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(null);
		BankingException ex= assertThrows(BankingException.class,
				()->service.getAccountDetail(12334l));
		assertEquals(ExceptionConstant.NO_ACCOUNT,ex.getMessage());
	}
	
	@Test
	void getMiniStatement() throws BankingException {
		List<TransactionEntity> list= new ArrayList<>();
		list.add(generateTransactionEntity());
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(generateAccountEntity());
		Mockito.when(trans.findTop20ByAccountIdOrderByTransactionIdDesc(Mockito.any())).thenReturn(list);
		service.getMiniStatement(12334l);
		assertEquals(1,service.getMiniStatement(12334l).size());
	}
	
	@Test
	void getMiniStatementException() throws BankingException {
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(null);
		BankingException ex= assertThrows(BankingException.class,
				()->service.getMiniStatement(12334l));
		assertEquals(ExceptionConstant.NO_ACCOUNT,ex.getMessage());
	}
	
	@Test
	void createAccount() throws BankingException {
		Mockito.when(account.save(Mockito.any())).thenReturn(generateAccountEntity());
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(generateAccountEntity());
		Mockito.when(trans.save(Mockito.any())).thenReturn(generateTransactionEntity());
		AccountDto dto=service.createAccount(generateAccountDto());
		assertNotNull(dto);
	}
	
	@Test
	void validateAccount1() throws BankingException {
		AccountDto account=generateAccountDto();
		account.setAccountHolderName(null);
		BankingException ex= assertThrows(BankingException.class,
				()->service.validateAccount(account));
		assertEquals(ExceptionConstant.ACCOUNT_HOLDER_NAME,ex.getMessage());
		account.setAccountHolderName("");
		BankingException ex1= assertThrows(BankingException.class,
				()->service.validateAccount(account));
		assertEquals(ExceptionConstant.ACCOUNT_HOLDER_NAME,ex1.getMessage());
	}
	
	@Test
	void validateAccount2() throws BankingException {
		AccountDto account=generateAccountDto();
		account.setAccountBalance(200d);
		BankingException ex= assertThrows(BankingException.class,
				()->service.validateAccount(account));
		assertEquals(ExceptionConstant.SAVING_ACCOUNT_BALNCE,ex.getMessage());
		account.setAccountBalance(null);
		BankingException ex1= assertThrows(BankingException.class,
				()->service.validateAccount(account));
		assertEquals(ExceptionConstant.TRANSACTION_AMOUNT,ex1.getMessage());
		account.setAccountBalance(0.0d);
		BankingException ex2= assertThrows(BankingException.class,
				()->service.validateAccount(account));
		assertEquals(ExceptionConstant.SAVING_ACCOUNT_BALNCE,ex2.getMessage());
	}
	
	@Test
	void validateAccount3() throws BankingException {
		AccountDto account=generateAccountDto();
		account.setAccountType("");
		BankingException ex= assertThrows(BankingException.class,
				()->service.validateAccount(account));
		assertEquals(ExceptionConstant.ACCOUNT_TYPE,ex.getMessage());
	}
	
	@Test
	void validateCreditException() throws BankingException {
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(null);
		BankingException ex= assertThrows(BankingException.class,
				()->service.creditTransaction(1223l, generateTransactionDto(), true, true));
		assertEquals(ExceptionConstant.NO_ACCOUNT,ex.getMessage());
	}
	
	@Test
	void validateCredit() throws BankingException {
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(generateAccountEntity()).thenReturn(generateAccountEntity());
		Mockito.when(trans.save(Mockito.any())).thenReturn(generateTransactionEntity());
		assertEquals(Constant.TRANSACTION_SUCCESS,service.creditTransaction(1223l, generateTransactionDto(), true, true));
	}
	
	@Test
	void validateCredit2() throws BankingException {
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(generateAccountEntity()).thenReturn(generateAccountEntity());
		Mockito.when(trans.save(Mockito.any())).thenReturn(generateTransactionEntity());
		assertEquals(Constant.TRANSACTION_SUCCESS,service.creditTransaction(1223l, generateTransactionDto(), false, true));
	}
	
	@Test
	void validatedebitException() throws BankingException {
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(null);
		BankingException ex= assertThrows(BankingException.class,
				()->service.debitTransaction(1223l, generateTransactionDto(), true, true));
		assertEquals(ExceptionConstant.NO_ACCOUNT,ex.getMessage());
	}
	
	@Test
	void validateDebitException2() throws BankingException {
		AccountEntity entity=generateAccountEntity();
		entity.setAccountBalance(500d);
		entity.setAccountType("Saving");
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(entity).thenReturn(entity);
		Mockito.when(trans.save(Mockito.any())).thenReturn(generateTransactionEntity());
		BankingException ex= assertThrows(BankingException.class,
				()->service.debitTransaction(1223l, generateTransactionDto(), true, true));
		assertEquals(ExceptionConstant.INSUFFICIENT_BALANCE,ex.getMessage());
	}
	
	@Test
	void validateDebit() throws BankingException {
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(generateAccountEntity()).thenReturn(generateAccountEntity());
		Mockito.when(trans.save(Mockito.any())).thenReturn(generateTransactionEntity());
		assertEquals(Constant.TRANSACTION_SUCCESS,service.debitTransaction(1223l, generateTransactionDto(), true, true));
	}
	
	@Test
	void validateDebit2() throws BankingException {
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(generateAccountEntity()).thenReturn(generateAccountEntity());
		Mockito.when(trans.save(Mockito.any())).thenReturn(generateTransactionEntity());
		assertEquals(Constant.TRANSACTION_SUCCESS,service.debitTransaction(1223l, generateTransactionDto(), false, true));
	}
	
	@Test
	void validateTransaction() throws BankingException {
		TransactionDto transactionDto = generateTransactionDto();
		transactionDto.setTransactionAccountNumber(null);
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(generateAccountEntity());
		BankingException ex= assertThrows(BankingException.class,
				()->service.validateTransaction(transactionDto, false, 1234l));
		assertEquals(ExceptionConstant.RECEIVER_ACCOUNT,ex.getMessage());
		
	}
	
	@Test
	void validateTransaction1() throws BankingException {
		TransactionDto transactionDto = generateTransactionDto();
		transactionDto.setTransactionAccountNumber(1234l);
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(null);
		BankingException ex= assertThrows(BankingException.class,
				()->service.validateTransaction(transactionDto, false, 1234l));
		assertEquals(ExceptionConstant.RECEIVER_ACCOUNT,ex.getMessage());
		
	}
	@Test
	void validateTransaction2() throws BankingException {
		TransactionDto transactionDto = generateTransactionDto();
		transactionDto.setTransactionAccountNumber(1234l);
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(generateAccountEntity());
		BankingException ex= assertThrows(BankingException.class,
				()->service.validateTransaction(transactionDto, false, 1234l));
		assertEquals(ExceptionConstant.SAME_ACCOUNT_TRANSFER,ex.getMessage());
		
	}
	@Test
	void validateTransaction3() throws BankingException {
		TransactionDto transactionDto = generateTransactionDto();
		transactionDto.setTransactionAmount(0.0d);
		Mockito.when(account.findByAccountNumber(Mockito.any())).thenReturn(generateAccountEntity());
		BankingException ex= assertThrows(BankingException.class,
				()->service.validateTransaction(transactionDto, false, 1234l));
		assertEquals(ExceptionConstant.TRANSACTION_AMOUNT,ex.getMessage());
		
	}
	
	@Test
	void constantTest() {
		Constant c=new Constant();
		ExceptionConstant e=new ExceptionConstant();
		BankingUtils util=new BankingUtils();
		assertNotNull(c);
		assertNotNull(e);
		assertNotNull(util);
	}
}
