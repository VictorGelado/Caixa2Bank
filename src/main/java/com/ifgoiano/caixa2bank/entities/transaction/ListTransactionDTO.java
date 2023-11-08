package com.ifgoiano.caixa2bank.entities.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ListTransactionDTO(Long id, String sender, BigDecimal value, String receiver, LocalDateTime time) {
}
