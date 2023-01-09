package com.example.bankingapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

/**
 * Account service class is used to perform business logic for banking app
 *
 */
@Service
@Transactional
public class AccountService {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	private static final List<String> accountType = Arrays.asList(new String[] {Constant.SAVING,Constant.SALARY});
	
	/**
	 * Method used by admin to see the available accounts
	 * 
	 * @return List<AccountDto>
	 */
	public List<AccountDto> getAllAccountDetail(){
		List<AccountEntity> accountEntityList = accountRepository.findAll();
		List<AccountDto> accountDtoList = new ArrayList<>();
		for(AccountEntity accountEntity : accountEntityList) {
			AccountDto accountDto= AccountDto.convertEntityToDto(accountEntity);
			accountDtoList.add(accountDto);
		}
		return accountDtoList;
	}
	
	/**
	 * Get endpoint used to see the account details based on account number
	 * 
	 * @param accountNumber
	 * @return AccountDto
	 * @throws BankingException
	 */
	public AccountDto getAccountDetail(Long accountNumber) throws BankingException{
		AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber);
		if(accountEntity != null) {
			return AccountDto.convertEntityToDto(accountEntity);
		}else {
			throw new BankingException(ExceptionConstant.NO_ACCOUNT);
		}
	}
	
	/**
	 * Get the latest 20 transactions as mini statement for an account
	 * 
	 * @param accountNumber
	 * @return List<TransactionDto>
	 * @throws BankingException
	 */
	public List<TransactionDto> getMiniStatement(Long accountNumber) throws BankingException{
		List<TransactionDto> transactionDtoList = new ArrayList<>();
		AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber);
		if(accountEntity != null) {
			List<TransactionEntity> transactionEntityList =transactionRepository.findTop20ByAccountIdOrderByTransactionIdDesc(accountEntity.getAccountId());
			for(TransactionEntity transactionEntity:transactionEntityList) {
				TransactionDto transactionDto = TransactionDto.convertEntityToDto(transactionEntity);
				transactionDtoList.add(transactionDto);
			}
		}else {
			throw new BankingException(ExceptionConstant.NO_ACCOUNT);
		}
		return transactionDtoList;
	}
	
	
	/**
	 * Create account for the customer
	 * 
	 * @param accountDto
	 * @return AccountDto
	 * @throws BankingException
	 */
	public AccountDto createAccount(AccountDto accountDto) throws BankingException{
		//validate account detail entered by user
		validateAccount(accountDto);
		AccountEntity accountEntity=AccountDto.convertDtoToEntity(accountDto);
		accountEntity.setCreatedTime(new Date());
		accountEntity.setAccountNumber(BankingUtils.generateAccountId());
		accountEntity.setAccountBalance(0.0d);
		accountEntity = accountRepository.save(accountEntity);
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTransactionAccountHolderName("System");
		transactionDto.setTransactionAmount(accountDto.getAccountBalance());
		transactionDto.setAmountBeforeTransaction(0.0d);
		creditTransaction(accountEntity.getAccountNumber(), transactionDto, true, null);
		return AccountDto.convertEntityToDto(accountEntity);
	}
	
	/**
	 * To validate the account details given by user
	 * 
	 * @param accountDto
	 * @throws BankingException
	 */
	public void validateAccount(AccountDto accountDto)  throws BankingException{
		if(accountDto.getAccountHolderName() ==null || accountDto.getAccountHolderName().trim().equals("")) {
			throw new BankingException(ExceptionConstant.ACCOUNT_HOLDER_NAME);
		}
		if(!accountType.contains(accountDto.getAccountType())) {
			throw new BankingException(ExceptionConstant.ACCOUNT_TYPE);
		}
		if(accountDto.getAccountBalance() == null) {
			throw new BankingException(ExceptionConstant.TRANSACTION_AMOUNT);
		}
		if(accountDto.getAccountType().equals(Constant.SAVING) && accountDto.getAccountBalance()<500) {
			throw new BankingException(ExceptionConstant.SAVING_ACCOUNT_BALNCE);
		}
	}
	
	/**
	 * Method to credit amount into an account
	 * 
	 * @param accountNumber
	 * @param transactionDto
	 * @param atmMachine
	 * @param controllerFlag
	 * @return
	 * @throws BankingException
	 */
	public String creditTransaction(Long accountNumber, TransactionDto transactionDto, Boolean atmMachine, Boolean controllerFlag) throws BankingException {
		AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber);
		if(accountEntity != null) {
			if(atmMachine.equals(true)) {
				if(transactionDto.getTransactionAccountHolderName()== null)
					transactionDto.setTransactionAccountHolderName(Constant.ATM);
				transactionDto.setTransactionAccountNumber(null);
			}
			//validate transaction detail entered by user
			validateTransaction(transactionDto, atmMachine, accountNumber);
			Double amount= accountEntity.getAccountBalance()+transactionDto.getTransactionAmount();
			TransactionEntity transactionEntity =TransactionDto.convertDtoToEntity(transactionDto);
			transactionEntity.setCreatedTime(new Date());
			transactionEntity.setAccountId(accountEntity.getAccountId());
			transactionEntity.setAmountBeforeTransaction(accountEntity.getAccountBalance());
			transactionEntity.setAmountAfterTransaction(amount);
			transactionEntity.setTransactionType(Constant.CREDIT);
			
			//If bank transfer debit same amount from transaction account
			if(!atmMachine.equals(true) && controllerFlag!=null) {
				TransactionDto transactionDto1 =new TransactionDto();
				transactionDto1.setTransactionAmount(transactionDto.getTransactionAmount());
				transactionDto1.setTransactionAccountNumber(accountNumber);
				transactionDto1.setTransactionAccountHolderName(accountEntity.getAccountHolderName());
				AccountEntity accountEntity1 = accountRepository.findByAccountNumber(transactionDto.getTransactionAccountNumber());
				if(accountEntity1 != null) {
					transactionEntity.setTransactionAccountHolderName(accountEntity1.getAccountHolderName());
				}
				else {
					throw new BankingException(ExceptionConstant.RECEIVER_ACCOUNT);
				}
				debitTransaction(transactionDto.getTransactionAccountNumber(), transactionDto1, atmMachine, null);
				
			}
			//Saving updated amount in account
			accountEntity.setAccountBalance(amount);
			accountRepository.save(accountEntity);
			
			transactionEntity.setTransactionNumber(BankingUtils.generateTransactionId());
			transactionRepository.save(transactionEntity);
		}else {
			throw new BankingException(ExceptionConstant.NO_ACCOUNT);
		}
		return Constant.TRANSACTION_SUCCESS;
	}
	
	/**
	 * Method to debit amount from an account
	 * 
	 * @param accountNumber
	 * @param transactionDto
	 * @param atmMachine
	 * @param controllerFlag
	 * @return
	 * @throws BankingException
	 */
	public String debitTransaction(Long accountNumber, TransactionDto transactionDto, Boolean atmMachine, Boolean controllerFlag) throws BankingException {
		AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber);
		if(accountEntity != null) {
			if(atmMachine.equals(true)) {
				if(transactionDto.getTransactionAccountHolderName()== null)
					transactionDto.setTransactionAccountHolderName(Constant.ATM);
				transactionDto.setTransactionAccountNumber(null);
			}
			//validate transaction detail entered by user
			validateTransaction(transactionDto, atmMachine, accountNumber);
			
			Double amount= accountEntity.getAccountBalance()-transactionDto.getTransactionAmount();
			if(( amount<500 && accountEntity.getAccountType().equals(Constant.SAVING)) ||( amount<0 && accountEntity.getAccountType().equals(Constant.SALARY))) {
				throw new BankingException(ExceptionConstant.INSUFFICIENT_BALANCE);
			}

			TransactionEntity transactionEntity =TransactionDto.convertDtoToEntity(transactionDto);
			transactionEntity.setAmountBeforeTransaction(accountEntity.getAccountBalance());
			transactionEntity.setAmountAfterTransaction(amount);
			transactionEntity.setCreatedTime(new Date());
			transactionEntity.setAccountId(accountEntity.getAccountId());
			transactionEntity.setTransactionType(Constant.DEBIT);
			
			//If bank transfer credit same amount to transaction account
			if(!atmMachine.equals(true) && controllerFlag!=null) {
				TransactionDto transactionDto1 =new TransactionDto();
				transactionDto1.setTransactionAmount(transactionDto.getTransactionAmount());
				transactionDto1.setTransactionAccountNumber(accountNumber);
				transactionDto1.setTransactionAccountHolderName(accountEntity.getAccountHolderName());
				AccountEntity accountEntity1 = accountRepository.findByAccountNumber(transactionDto.getTransactionAccountNumber());
				if(accountEntity1 != null) {
					transactionEntity.setTransactionAccountHolderName(accountEntity1.getAccountHolderName());
				}
				else {
					throw new BankingException(ExceptionConstant.RECEIVER_ACCOUNT);
				}
				creditTransaction(transactionDto.getTransactionAccountNumber(), transactionDto1, atmMachine, null);
				
			}
			//Saving updated amount in account
			accountEntity.setAccountBalance(amount);
			accountRepository.save(accountEntity);
			
			transactionEntity.setTransactionNumber(BankingUtils.generateTransactionId());
			transactionRepository.save(transactionEntity);
		}else {
			throw new BankingException(ExceptionConstant.NO_ACCOUNT);
		}
		return Constant.TRANSACTION_SUCCESS;
	}
	
	/**
	 * To validate the transaction details for an account
	 * 
	 * @param transactionDto
	 * @param atmMachine
	 * @throws BankingException
	 */
	public void validateTransaction(TransactionDto transactionDto, Boolean atmMachine,Long accountNumber) throws BankingException {
		if(atmMachine.equals(false)) {
			if(transactionDto.getTransactionAccountNumber() == null) {
				throw new BankingException(ExceptionConstant.RECEIVER_ACCOUNT);
			}
			AccountEntity accountEntity = accountRepository.findByAccountNumber(transactionDto.getTransactionAccountNumber());
			if(accountEntity == null) {
				throw new BankingException(ExceptionConstant.RECEIVER_ACCOUNT);
			}	
			if(accountNumber.equals(transactionDto.getTransactionAccountNumber())) {
				throw new BankingException(ExceptionConstant.SAME_ACCOUNT_TRANSFER);
			}
		}
		if(transactionDto.getTransactionAmount() == null || transactionDto.getTransactionAmount()==0) {
			throw new BankingException(ExceptionConstant.TRANSACTION_AMOUNT);
		}

	}

}
