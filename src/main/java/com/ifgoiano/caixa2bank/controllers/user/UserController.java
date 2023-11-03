package com.ifgoiano.caixa2bank.controllers.user;

import com.ifgoiano.caixa2bank.email.EmailService;
import com.ifgoiano.caixa2bank.entities.account.Account;
import com.ifgoiano.caixa2bank.entities.account.NewAccountDTO;
import com.ifgoiano.caixa2bank.entities.user.User;
import com.ifgoiano.caixa2bank.services.account.AccountService;
import com.ifgoiano.caixa2bank.services.transaction.TransactionService;
import com.ifgoiano.caixa2bank.services.user.UserDataService;
import com.ifgoiano.caixa2bank.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserDataService userDataService;

	@PostMapping("/register")
	public String registerUser(NewAccountDTO newAccountDTO) {
		User user = new User(newAccountDTO);

		userService.saveUser(user);

		Account account = new Account(newAccountDTO.password() ,user);

		accountService.saveAccount(account);

		return "redirect:/user/login";
	}

	@GetMapping("/dashboard")
	public ModelAndView getDashboardPage() {
		ModelAndView view = new ModelAndView("dashboard");

		view.addObject("userData", userDataService.data());

		return view;
	}



}
