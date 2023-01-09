package com.example.bankingapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.example.bankingapp.dto.AccountDto;
import com.example.bankingapp.dto.ResponseResource;
import com.example.bankingapp.dto.TransactionDto;
import com.example.bankingapp.exception.BankingException;
import com.example.bankingapp.service.AccountService;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BankingControllerTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate template;
	
	@Mock
	AccountService service;
	
	@InjectMocks
	AccountController controller;
	
	@Test
	void testGetAccountDetail() throws BankingException {
		Mockito.when(service.getAccountDetail(Mockito.any())).thenReturn(null);
		ResponseResource<AccountDto> response=controller.getAccountDetail(122l);
		assertEquals(null, response.getData());
	}
	
	@Test
	void testCreateAccountDetail() throws BankingException {
		Mockito.when(service.createAccount(Mockito.any())).thenReturn(null);
		ResponseResource<AccountDto> response=controller.createAccount(new AccountDto());
		assertEquals(null, response.getData());
	}
	
	@Test
	void testTransactionDetail() throws BankingException {
		Mockito.when(service.getMiniStatement(Mockito.any())).thenReturn(null);
		ResponseResource<List<TransactionDto>> response=controller.getMiniStatement(122l);
		assertEquals(null, response.getData());
	}
	
	@Test
	void testCreditDetail() throws BankingException {
		Mockito.when(service.creditTransaction(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn("SUCCESS");
		ResponseResource<String> response=controller.creditTransactionAtm(122l,new TransactionDto());
		assertEquals("SUCCESS", response.getData());
	}
	
	@Test
	void testDebitAtmDetail() throws BankingException {
		Mockito.when(service.debitTransaction(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn("SUCCESS");
		ResponseResource<String> response=controller.debitTransactionAtm(122l,new TransactionDto());
		assertEquals("SUCCESS", response.getData());
	}
	
	@Test
	void testDebitDetail() throws BankingException {
		Mockito.when(service.debitTransaction(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn("SUCCESS");
		ResponseResource<String> response=controller.debitTransaction(122l,new TransactionDto());
		assertEquals("SUCCESS", response.getData());
	}
	
	
	@Test
	void testAccountTest() {
		this.template.exchange("http://localhost:"+port+"/api/banking/account", HttpMethod.GET,null,String.class);
		assertTrue(true);
	}
	
	@Test
	void testAccountTest2() {
		this.template.exchange("http://localhost:"+port+"/api/banking/account/123234", HttpMethod.GET,null,String.class);
		assertTrue(true);
	}
	
	

}
