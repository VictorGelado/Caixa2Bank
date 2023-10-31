package com.ifgoiano.caixa2bank.controllers.transaction;

import com.ifgoiano.caixa2bank.entities.account.Account;
import com.ifgoiano.caixa2bank.entities.transaction.Transaction;
import com.ifgoiano.caixa2bank.entities.transaction.TransactionDTO;
import com.ifgoiano.caixa2bank.services.account.AccountService;
import com.ifgoiano.caixa2bank.services.transaction.TransactionService;
import com.ifgoiano.caixa2bank.services.user.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/send")
    public ModelAndView sendCash(TransactionDTO tr) {
        transactionService.send(tr);
        ModelAndView view = new ModelAndView("dashboard");

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        view.addObject("userData", userDataService.data());

        return view;
    }

    @GetMapping("/list")
    public ModelAndView listAllTransactions(TransactionDTO tr) {
        ModelAndView view = new ModelAndView("transactions");

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByLogin(principal.getUsername());

        List<Transaction> transactions = transactionService.findAllTransactions(account);

        view.addObject("transactions", transactions);

        return view;
    }
}
