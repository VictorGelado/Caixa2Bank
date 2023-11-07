package com.ifgoiano.caixa2bank.entities.account;

import com.ifgoiano.caixa2bank.controllers.account.KeysExistisDTO;
import com.ifgoiano.caixa2bank.entities.transaction.Transaction;
import com.ifgoiano.caixa2bank.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="account")
public class Account {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="num_seq")
    @SequenceGenerator(name="num_seq", sequenceName="number_seq", allocationSize=1, initialValue=100000)
    @Column(name="number", nullable=false, length = 6, updatable = false)
    private Integer number;

    @Column(name="password", nullable=false)
    private String password;

    @Column(name="balance", nullable=false, columnDefinition="numeric(20,2)")
    private BigDecimal balance;

    @Column(name="pix-random-key", nullable=true)
    private String pixRandomKey;

    @Column(name="pix-phone", nullable=true)
    private String pixPhone;

    @Column(name="pix-email", nullable=true)
    private String pixEmail;

    @Column(name="pix-cpf", nullable=true)
    private String pixCpf;

    @Column(name="password-transaction", nullable = false)
    private String passwordTransaction;

    @OneToOne
    User user;

    @OneToMany(mappedBy="sender")
    List<Transaction> transactionsSent;

    @OneToMany(mappedBy="receiver")
    List<Transaction> transactionsReceived;

    public Account(String password, String passwordTransaction, User user) {
        this.password = password;
        this.setPasswordTransaction(passwordTransaction);
        this.user = user;
        this.setBalance(BigDecimal.valueOf(0));
    }

    public List<String> getKeys() {
        ArrayList<String> keys = new ArrayList<>();

        if (this.getPixCpf() != null) keys.add(this.getPixCpf());
        if (this.getPixRandomKey() != null) keys.add(this.getPixRandomKey());
        if (this.getPixEmail() != null) keys.add(this.getPixEmail());
        if (this.getPixPhone() != null) keys.add(this.getPixPhone());

        return keys;
    }

    public KeysExistisDTO checkRegisteredKeys() {

        KeysExistisDTO registeredKeys = new KeysExistisDTO(
            this.getPixCpf() == null,
            this.getPixRandomKey() == null,
            this.getPixPhone() == null,
            this.getPixEmail() == null
        );

        return registeredKeys;
    }

}
