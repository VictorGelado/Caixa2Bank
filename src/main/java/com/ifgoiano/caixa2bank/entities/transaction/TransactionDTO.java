package com.ifgoiano.caixa2bank.entities.transaction;

import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, String receiver, String passwordTransaction) {
}
