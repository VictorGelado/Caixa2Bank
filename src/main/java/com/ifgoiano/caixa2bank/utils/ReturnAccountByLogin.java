package com.ifgoiano.caixa2bank.utils;

import com.ifgoiano.caixa2bank.entities.account.Account;
import com.ifgoiano.caixa2bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReturnAccountByLogin {

    @Autowired
    AccountRepository accountRepository;

    public Account findByLogin(String login) {
        Account account;

        if (login.length() == 11) {
            account = accountRepository.findByCpf(login);
        } else if (login.contains("@")) {
            account = accountRepository.findByEmail(login);
        } else {
            account = accountRepository.findByNumber(Integer.parseInt(login));
        }

        return account;
    }

}
