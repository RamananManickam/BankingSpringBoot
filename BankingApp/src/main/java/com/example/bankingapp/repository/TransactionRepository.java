package com.example.bankingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankingapp.entity.TransactionEntity;

/**
 * Transaction repository is used to access the transaction detail from database
 *
 */
public interface TransactionRepository extends JpaRepository<TransactionEntity,Integer>{

	List<TransactionEntity> findTop20ByAccountIdOrderByTransactionIdDesc(Integer accountId);
}
