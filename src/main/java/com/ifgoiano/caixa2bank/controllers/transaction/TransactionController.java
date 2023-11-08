package com.ifgoiano.caixa2bank.controllers.transaction;

import com.ifgoiano.caixa2bank.entities.account.Account;
import com.ifgoiano.caixa2bank.entities.transaction.ListTransactionDTO;
import com.ifgoiano.caixa2bank.entities.transaction.TransactionDTO;
import com.ifgoiano.caixa2bank.services.account.AccountService;
import com.ifgoiano.caixa2bank.services.pdf.PdfGenerator;
import com.ifgoiano.caixa2bank.services.transaction.TransactionService;
import com.ifgoiano.caixa2bank.services.user.UserDataService;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Autowired
    private PdfGenerator pdfGenerator;

    @PostMapping("/send")
    public ModelAndView sendCash(TransactionDTO tr) {
        ModelAndView view = new ModelAndView("user-dashboard");

        transactionService.send(tr);

        view.addObject("userData", userDataService.data());

        return view;
    }

    @GetMapping("/list")
    public ModelAndView listAllTransactions() {
        ModelAndView view = new ModelAndView("transactions");

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByLogin(principal.getUsername());

        List<ListTransactionDTO> transactions = transactionService.findAllTransactions(account);

        view.addObject("transactions", transactions);

        return view;
    }

    @GetMapping("/pdf")
    public String generatePdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerkey = "Content-Disposition";
        String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerkey, headervalue);

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByLogin(principal.getUsername());

        List<ListTransactionDTO> transactions = transactionService.findAllTransactions(account);

        pdfGenerator.generatePdf(response, transactions);
        return "redirect:/user/dashboard";
    }
}
