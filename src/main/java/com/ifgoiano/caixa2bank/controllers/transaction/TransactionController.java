package com.ifgoiano.caixa2bank.controllers.transaction;

import com.ifgoiano.caixa2bank.entities.transaction.TransactionDTO;
import com.ifgoiano.caixa2bank.services.transaction.TransactionService;
import com.ifgoiano.caixa2bank.services.user.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserDataService userDataService;

    @PostMapping("/send")
    public ModelAndView sendCash(TransactionDTO tr) {
        transactionService.send(tr);
        ModelAndView view = new ModelAndView("dashboard");

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        view.addObject("userData", userDataService.data());

        return view;
    }
}