package com.example.bankingapp.controller;

import java.util.List;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankingapp.constant.Constant;
import com.example.bankingapp.dto.AccountDto;
import com.example.bankingapp.dto.ResponseResource;
import com.example.bankingapp.dto.TransactionDto;
import com.example.bankingapp.exception.BankingException;
import com.example.bankingapp.service.AccountService;

/**
 * Account controller hold list of api's to perform business logic of banking app
 *
 */

@RestController
@RequestMapping("/banking")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	/**
	 * Method used by admin to see the available accounts
	 * 
	 * @return List<AccountDto>
	 */
	@GetMapping("/account")
	public ResponseResource<List<AccountDto>> getAllAccountDetail(){
		return new ResponseResource<List<AccountDto>>(Constant.OK, Constant.SUCCESS,
				accountService.getAllAccountDetail(),MDC.get(Constant.MDC_TOKEN));
	}
	
	/**
	 * Get endpoint used to see the account details based on account number
	 * 
	 * @param accountNumber
	 * @return AccountDto
	 * @throws BankingException
	 */
	@GetMapping("/account/{accountNumber}")
	public ResponseResource<AccountDto> getAccountDetail(@PathVariable("accountNumber") Long accountNumber) throws BankingException{
		return new ResponseResource<>(Constant.OK, Constant.SUCCESS,
				accountService.getAccountDetail(accountNumber),MDC.get(Constant.MDC_TOKEN));
	}
	
	/**
	 * Get the latest 20 transactions as mini statement for an account
	 * 
	 * @param accountNumber
	 * @return List<TransactionDto>
	 * @throws BankingException
	 */
	@GetMapping("/account/ministatement/{accountNumber}")
	public ResponseResource<List<TransactionDto>> getMiniStatement(@PathVariable("accountNumber") Long accountNumber) throws BankingException{
		return new ResponseResource<List<TransactionDto>>(Constant.OK, Constant.SUCCESS,
				accountService.getMiniStatement(accountNumber),MDC.get(Constant.MDC_TOKEN));
	}
	
	/**
	 * Create account for the customer
	 * 
	 * @param accountDto
	 * @return AccountDto
	 * @throws BankingException
	 */
	@PostMapping("/account/create")
	public ResponseResource<AccountDto> createAccount(@RequestBody AccountDto accountDto) throws BankingException{
		return new ResponseResource<>(Constant.OK, Constant.SUCCESS,
				accountService.createAccount(accountDto),MDC.get(Constant.MDC_TOKEN));
	}
	
	/**
	 * Method to credit amount into an account from atm
	 * 
	 * @param accountNumber
	 * @param transactionDto
	 * @param atmMachine
	 * @param controllerFlag
	 * @return
	 * @throws BankingException
	 */
	@PostMapping("/account/credit/atm/{accountNumber}")
	public ResponseResource<String> creditTransactionAtm(@PathVariable("accountNumber") Long accountNumber,@RequestBody TransactionDto transactionDto) throws BankingException {
		return new ResponseResource<>(Constant.OK, Constant.SUCCESS,
				accountService.creditTransaction(accountNumber, transactionDto, true, true),MDC.get(Constant.MDC_TOKEN));
	}
	
	/**
	 * Method to debit amount from an account from atm
	 * 
	 * @param accountNumber
	 * @param transactionDto
	 * @param atmMachine
	 * @param controllerFlag
	 * @return
	 * @throws BankingException
	 */
	@PostMapping("/account/debit/atm/{accountNumber}")
	public ResponseResource<String> debitTransactionAtm(@PathVariable("accountNumber") Long accountNumber,@RequestBody TransactionDto transactionDto) throws BankingException {
		return new ResponseResource<>(Constant.OK, Constant.SUCCESS,
				accountService.debitTransaction(accountNumber, transactionDto, true, true),MDC.get(Constant.MDC_TOKEN));
	}
	
	/**
	 * Method to debit amount from an account into another account
	 * 
	 * @param accountNumber
	 * @param transactionDto
	 * @param atmMachine
	 * @param controllerFlag
	 * @return
	 * @throws BankingException
	 */
	@PostMapping("/account/transfer/{accountNumber}")
	public ResponseResource<String> debitTransaction(@PathVariable("accountNumber") Long accountNumber,@RequestBody TransactionDto transactionDto) throws BankingException {
		return new ResponseResource<>(Constant.OK, Constant.SUCCESS,
				accountService.debitTransaction(accountNumber, transactionDto, false, true),MDC.get(Constant.MDC_TOKEN));
	}
		

}
