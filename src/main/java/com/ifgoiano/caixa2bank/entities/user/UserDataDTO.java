package com.ifgoiano.caixa2bank.entities.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public record UserDataDTO(String username, int number, BigDecimal balance, String cpf) {

}
