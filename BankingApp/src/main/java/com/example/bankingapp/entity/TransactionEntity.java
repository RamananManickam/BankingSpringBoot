package com.example.bankingapp.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Transaction Entity class is used to store transaction related details
 *
 */
@Entity(name="Transaction")
public class TransactionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionId;
	
	private Long transactionNumber;
	
	private Integer accountId;
	
	private String transactionType;
	
	private Double amountBeforeTransaction;
	
	private Double transactionAmount;
	
	private Double amountAfterTransaction;
	
	private Long transactionAccountNumber;
	
	private String transactionAccountHolderName;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Long getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(Long transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Double getAmountBeforeTransaction() {
		return amountBeforeTransaction;
	}

	public void setAmountBeforeTransaction(Double amountBeforeTransaction) {
		this.amountBeforeTransaction = amountBeforeTransaction;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Double getAmountAfterTransaction() {
		return amountAfterTransaction;
	}

	public void setAmountAfterTransaction(Double amountAfterTransaction) {
		this.amountAfterTransaction = amountAfterTransaction;
	}

	public Long getTransactionAccountNumber() {
		return transactionAccountNumber;
	}

	public void setTransactionAccountNumber(Long transactionAccountNumber) {
		this.transactionAccountNumber = transactionAccountNumber;
	}

	public String getTransactionAccountHolderName() {
		return transactionAccountHolderName;
	}

	public void setTransactionAccountHolderName(String transactionAccountHolderName) {
		this.transactionAccountHolderName = transactionAccountHolderName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	

}
