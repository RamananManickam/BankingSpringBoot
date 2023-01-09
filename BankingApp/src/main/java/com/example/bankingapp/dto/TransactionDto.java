package com.example.bankingapp.dto;

import java.util.Date;

import com.example.bankingapp.entity.TransactionEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Transaction dto class is used to transfer transaction related details
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDto {
	
	private Long transactionNumber;
	
	private String transactionType;
	
	private Double amountBeforeTransaction;
	
	private Double transactionAmount;
	
	private Double amountAfterTransaction;
	
	private Long transactionAccountNumber;
	
	private String transactionAccountHolderName;
	
	private Date createdTime;
	
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
	
	/**
	 * Generate transaction entity from dto
	 * 
	 * @param dto
	 * @return TransactionEntity
	 */
	public static TransactionEntity convertDtoToEntity(TransactionDto dto) {
		TransactionEntity entity = new TransactionEntity();
		entity.setAmountAfterTransaction(dto.getAmountAfterTransaction());
		entity.setAmountBeforeTransaction(dto.getAmountBeforeTransaction());
		entity.setCreatedTime(dto.getCreatedTime());
		entity.setTransactionAccountHolderName(dto.getTransactionAccountHolderName());
		entity.setTransactionAccountNumber(dto.getTransactionAccountNumber());
		entity.setTransactionAmount(dto.getTransactionAmount());
		entity.setTransactionNumber(dto.getTransactionNumber());
		entity.setTransactionType(dto.getTransactionType());
		return entity;
	}
	
	/**
	 * Generate transaction dto from entity
	 * 
	 * @param entity
	 * @return TransactionDto
	 */
	public static TransactionDto convertEntityToDto(TransactionEntity entity) {
		TransactionDto dto = new TransactionDto();
		dto.setAmountAfterTransaction(entity.getAmountAfterTransaction());
		dto.setAmountBeforeTransaction(entity.getAmountBeforeTransaction());
		dto.setCreatedTime(entity.getCreatedTime());
		dto.setTransactionAccountHolderName(entity.getTransactionAccountHolderName());
		dto.setTransactionAccountNumber(entity.getTransactionAccountNumber());
		dto.setTransactionAmount(entity.getTransactionAmount());
		dto.setTransactionNumber(entity.getTransactionNumber());
		dto.setTransactionType(entity.getTransactionType());
		return dto;
	}

	@Override
	public String toString() {
		return "TransactionDto [transactionNumber=" + transactionNumber + ", transactionType=" + transactionType
				+ ", amountBeforeTransaction=" + amountBeforeTransaction + ", transactionAmount=" + transactionAmount
				+ ", amountAfterTransaction=" + amountAfterTransaction + ", transactionAccountNumber="
				+ transactionAccountNumber + ", transactionAccountHolderName=" + transactionAccountHolderName
				+ ", createdTime=" + createdTime + "]";
	}
	
	
}
