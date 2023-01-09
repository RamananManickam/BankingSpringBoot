package com.example.bankingapp.dto;

import java.util.Date;

import com.example.bankingapp.entity.AccountEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Account dto class is used to transfer account related details
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto {
	
	private Long accountNumber;
	
	private String accountHolderName;
	
	private String accountType;
	
	private Double accountBalance;
	
	private Date createdTime;

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	/**
	 * Generate account entity from dto
	 * 
	 * @param dto
	 * @return AccountEntity
	 */
	public static AccountEntity convertDtoToEntity(AccountDto dto) {
		AccountEntity entity = new AccountEntity();
		entity.setAccountBalance(dto.getAccountBalance());
		entity.setAccountHolderName(dto.getAccountHolderName());
		entity.setAccountNumber(dto.getAccountNumber());
		entity.setAccountType(dto.getAccountType());
		entity.setCreatedTime(dto.getCreatedTime());
		return entity;
	}
	
	/**
	 * Generate account dto from entity
	 * 
	 * @param entity
	 * @return AccountDto
	 */
	public static AccountDto convertEntityToDto(AccountEntity entity) {
		AccountDto dto = new AccountDto();
		dto.setAccountBalance(entity.getAccountBalance());
		dto.setAccountHolderName(entity.getAccountHolderName());
		dto.setAccountNumber(entity.getAccountNumber());
		dto.setAccountType(entity.getAccountType());
		dto.setCreatedTime(entity.getCreatedTime());
		return dto;
	}

	@Override
	public String toString() {
		return "AccountDto [accountNumber=" + accountNumber + ", accountHolderName=" + accountHolderName
				+ ", accountType=" + accountType + ", accountBalance=" + accountBalance + ", createdTime=" + createdTime
				+ "]";
	}
	

}
