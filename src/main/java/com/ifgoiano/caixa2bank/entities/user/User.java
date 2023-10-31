package com.ifgoiano.caixa2bank.entities.user;

import com.ifgoiano.caixa2bank.entities.account.NewAccountDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_account")
public class User {
	@Id
	@Column(name = "cpf", unique = true, nullable=false, length = 11)
	private String cpf;

	@Column(name = "username", nullable=false)
	public String username;

	@Column(name = "email", nullable=false, unique = true)
	public String email;

	@Column(name = "phone", nullable=false, unique = true)
	public String phone;

	public User (NewAccountDTO newAccountDTO) {
		this.setUsername(newAccountDTO.username());
		this.setCpf(newAccountDTO.cpf());
		this.setEmail(newAccountDTO.email());
		this.setPhone(newAccountDTO.phone());
	}
}
