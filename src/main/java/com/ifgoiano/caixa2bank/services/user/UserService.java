package com.ifgoiano.caixa2bank.services.user;

import com.ifgoiano.caixa2bank.entities.account.Account;
import com.ifgoiano.caixa2bank.entities.account.NewAccountDTO;
import com.ifgoiano.caixa2bank.entities.user.User;
import com.ifgoiano.caixa2bank.repository.AccountRepository;
import com.ifgoiano.caixa2bank.repository.UserRepository;
import com.ifgoiano.caixa2bank.services.account.AccountService;
import com.ifgoiano.caixa2bank.websecurity.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    @Autowired
    private AccountService accountService;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void saveUser(User user) {
        repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Account account = accountService.findByLogin(login);

        if (account == null) throw new UsernameNotFoundException("User not exists");

        return new CustomUserDetails(login, account);
    }
}