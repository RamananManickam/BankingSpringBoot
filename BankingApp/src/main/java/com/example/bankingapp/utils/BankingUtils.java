package com.example.bankingapp.utils;

import java.util.Random;

/**
 * Hold common logic used inside the banking app
 *
 */
public class BankingUtils {

	static String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public static String generateUid(int length) {
		StringBuilder sb = new StringBuilder(length);
		 
		  for (int i = 0; i < length; i++) {
		   int index = (int)(alphaNumericString.length() * Math.random());
		   sb.append(alphaNumericString.charAt(index));
		  }
		 
		  return sb.toString();
	}
	
	public static long generateAccountId() {
		return (long) ((Math.random()*(9999999999l-1111111111l+1))+9999999999l);   
	}
	
	public static long generateTransactionId() {
		return (long) ((Math.random()*(999999999999l-111111111111l+1))+999999999999l);   
	}
}
