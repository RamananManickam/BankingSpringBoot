package com.example.bankingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankingapp.entity.AccountEntity;

/**
 * Account repository is used to access the account from database
 *
 */
public interface AccountRepository extends JpaRepository<AccountEntity, Integer>{
	
	AccountEntity findByAccountNumber(Long accountNumer);

}
