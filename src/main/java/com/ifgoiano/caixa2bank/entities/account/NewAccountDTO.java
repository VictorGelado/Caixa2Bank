package com.ifgoiano.caixa2bank.entities.account;

import com.ifgoiano.caixa2bank.entities.user.Authority;

import java.util.List;

public record NewAccountDTO(String username, String password, String cpf, String email, String phone) {
}
