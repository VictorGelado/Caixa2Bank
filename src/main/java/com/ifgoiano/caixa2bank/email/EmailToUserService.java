package com.ifgoiano.caixa2bank.email;

import com.ifgoiano.caixa2bank.entities.account.Account;
import com.ifgoiano.caixa2bank.services.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class EmailToUserService {

    @Autowired
    private EmailService emailService;

    @Autowired
    @Lazy
    private AccountService accountService;

    public void sendEmailCreatedAccount(String cpf) {
        Account account = accountService.findByLogin(cpf);

        String email = account.getUser().getEmail();
        String subject = "Conta criada com sucesso!";
        String msg = "O número da sua conta para o acesso é: " + account.getNumber();

        emailService.sendEmail(email, subject, msg);
    }

}
