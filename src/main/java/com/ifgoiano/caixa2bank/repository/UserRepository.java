package com.ifgoiano.caixa2bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifgoiano.caixa2bank.entities.user.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
	//@Query("select u from conta u where u.username like :username")
	//@Query("SELECT u FROM User u WHERE u.username = ?")
	User findByCpf(String cpf);

	List<User> findAll();

}
