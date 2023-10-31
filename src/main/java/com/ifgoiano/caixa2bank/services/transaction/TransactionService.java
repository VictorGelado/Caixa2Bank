package com.ifgoiano.caixa2bank.services.transaction;

import com.ifgoiano.caixa2bank.entities.account.Account;
import com.ifgoiano.caixa2bank.entities.transaction.Transaction;
import com.ifgoiano.caixa2bank.entities.transaction.TransactionDTO;
import com.ifgoiano.caixa2bank.repository.TransactionRepository;
import com.ifgoiano.caixa2bank.services.account.AccountService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    @SneakyThrows
    public void send(TransactionDTO tr) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal != null) {
            Account receiver = accountService.findByPix(tr.receiver());
            Account sender = accountService.findByLogin(principal.getUsername());

            if (receiver == sender) throw new Exception();

            if (receiver == null) receiver = accountService.findByNumberAccount(Integer.parseInt(tr.receiver()));

            Transaction transaction = new Transaction(tr.value()/*, Date.valueOf(LocalDate.now())*/, sender, receiver);

            transactionRepository.save(transaction);

            receiver.setBalance(receiver.getBalance().add(tr.value()));
            sender.setBalance(sender.getBalance().subtract(tr.value()));

            accountService.updateAccount(receiver);
            accountService.updateAccount(sender);
        }
    }

    public List<Transaction> findAllTransactions(Account account) {
        List<Transaction> allTransactions = transactionRepository.findAllTransactions(account);

        return allTransactions;
    }
}
