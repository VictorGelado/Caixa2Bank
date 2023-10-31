package com.ifgoiano.caixa2bank.services.account;

import com.ifgoiano.caixa2bank.utils.CheckIsUUID;
import com.ifgoiano.caixa2bank.utils.ReturnAccountByLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ifgoiano.caixa2bank.entities.account.Account;
import com.ifgoiano.caixa2bank.repository.AccountRepository;

import java.util.UUID;

@Service
public class AccountService {

	@Autowired
	AccountRepository repository;

	@Autowired
	CheckIsUUID checkIsUUID;

	@Autowired
	ReturnAccountByLogin returnAccountByLogin;

	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public void updateAccount(Account account) {
		repository.save(account);
	}
	
	public void saveAccount(Account account) {
		Account nAccount = new Account(passwordEncoder().encode(account.getPassword()), account.getUser());
		
		repository.save(nAccount);
	}

	public Account findByLogin(String login) {
		Account account = null;

		account = returnAccountByLogin.findByLogin(login);

		return account;
	}

	public Account findByNumberAccount(int number) {
		Account account = repository.findByNumberAccount(number);

		return account;
	}

	public void saveKey(String key) {
		UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Account account = this.findByLogin(principal.getUsername());

		if (key.equals("cpf")) {
			account.setPixCpf(account.getUser().getCpf());
		} else if (key.equals("random")) {
			UUID uuid;

			do {
				uuid = UUID.randomUUID();
			} while (repository.findByPix(uuid.toString()) != null); // Verify exists random key in data base

			account.setPixRandomKey(uuid.toString());
		} else if (key.equals("email")) {
			account.setPixEmail(account.getUser().getEmail());
		} else if (key.equals("phone")) {
			account.setPixPhone(account.getUser().getPhone());
		}

		this.updateAccount(account);
	}

	public Account findByPix(String key) {
		Account account = null;

		account = repository.findByPix(key);

		return account;
	}
}
