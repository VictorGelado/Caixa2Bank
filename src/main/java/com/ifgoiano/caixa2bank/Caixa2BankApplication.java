package com.ifgoiano.caixa2bank;

import com.ifgoiano.caixa2bank.entities.user.Authority;
import com.ifgoiano.caixa2bank.entities.user.User;
import com.ifgoiano.caixa2bank.entities.user.UserAdminDTO;
import com.ifgoiano.caixa2bank.services.authority.AuthorityService;
import com.ifgoiano.caixa2bank.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication()
public class Caixa2BankApplication implements CommandLineRunner {

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private UserService userService;


	public static void main(String[] args) {
		//System.out.println(new BCryptPasswordEncoder().encode("123"));
		SpringApplication.run(Caixa2BankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createAuthorities();
		createAdmin();
	}

	public void createAuthorities() {
		Authority authorityAdmin = new Authority("admin");
		Authority authorityUser = new Authority("user");

		List<Authority> authorities = new ArrayList<>();
		authorities.add(authorityAdmin);
		authorities.add(authorityUser);

		if (authorityService.findById(1L) == null && authorityService.findById(2L) == null) {
			authorityService.saveAllAuthority(authorities);
		}
	}

	public void createAdmin() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		UserAdminDTO newUser = new UserAdminDTO("admin", passwordEncoder.encode("admin"), "00000000011",
			"admin@gmail.com", "(62)900000000"
		);

		if (userService.findByLogin(newUser.cpf()) == null) {
			User user = new User(newUser);

			userService.saveAdmin(user);
		}
	}
}
