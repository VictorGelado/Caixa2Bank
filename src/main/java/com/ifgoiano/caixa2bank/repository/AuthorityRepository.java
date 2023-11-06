package com.ifgoiano.caixa2bank.repository;

import com.ifgoiano.caixa2bank.entities.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
