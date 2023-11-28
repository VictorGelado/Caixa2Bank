package com.ifgoiano.caixa2bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication()
public class Caixa2BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(Caixa2BankApplication.class, args);
    }
}
