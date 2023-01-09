package com.example.bankingapp.exception;

/**
 * Custom exception for banking application
 *
 */
@SuppressWarnings("serial")
public class BankingException extends Exception{
	
	public BankingException(String message) {
		super(message);
	}

}
