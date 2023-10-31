package com.ifgoiano.caixa2bank.services.transaction;

import com.ifgoiano.caixa2bank.entities.account.Account;
import com.ifgoiano.caixa2bank.entities.transaction.TransactionDTO;
import com.ifgoiano.caixa2bank.services.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private AccountService accountService;

    public void send(TransactionDTO tr) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       if (principal != null) {
            Account receiver = accountService.findByPix(tr.receiver());
            Account sender = accountService.findByLogin(principal.getUsername());

            if (receiver == null) receiver = accountService.findByNumberAccount(Integer.parseInt(tr.receiver()));

            receiver.setBalance(receiver.getBalance().add(tr.value()));
            sender.setBalance(sender.getBalance().subtract(tr.value()));

            accountService.updateAccount(receiver);
            accountService.updateAccount(sender);
       }
    }
}
