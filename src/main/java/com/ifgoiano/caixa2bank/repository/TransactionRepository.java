package com.ifgoiano.caixa2bank.repository;

import com.ifgoiano.caixa2bank.entities.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
