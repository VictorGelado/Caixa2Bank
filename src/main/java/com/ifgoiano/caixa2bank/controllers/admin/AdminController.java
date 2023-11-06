package com.ifgoiano.caixa2bank.controllers.admin;

import com.ifgoiano.caixa2bank.entities.account.Account;
import com.ifgoiano.caixa2bank.services.account.AccountService;
import com.ifgoiano.caixa2bank.services.user.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserDataService userDataService;

    @GetMapping("/list-all")
    public ModelAndView listAllUsers() {
        ModelAndView view = new ModelAndView("list-all");

        List<Account> all = accountService.findAll();

        view.addObject("all-users", all);

        return view;
    }

    @GetMapping("/dashboard")
    public String getDashboardPage(RedirectAttributes attr) {
        ModelAndView view = new ModelAndView("user-dashboard");

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        attr.addFlashAttribute("userData", principal.getUsername());

        return "admin-dashboard";
    }

}
