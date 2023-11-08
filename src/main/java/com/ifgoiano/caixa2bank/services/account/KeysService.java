package com.ifgoiano.caixa2bank.services.account;

import com.ifgoiano.caixa2bank.entities.account.Account;
import com.ifgoiano.caixa2bank.entities.account.KeysExistisDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeysService {

    @Autowired
    AccountService accountService;

    public List<String> getKeysAccount() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = accountService.findByLogin(principal.getUsername());

        List<String> keys = account.getKeys();

        return keys;
    }

    public KeysExistisDTO checkRegisteredKeys() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = accountService.findByLogin(principal.getUsername());

        return account.checkRegisteredKeys();
    }

}
