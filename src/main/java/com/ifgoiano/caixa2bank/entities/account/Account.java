package com.ifgoiano.caixa2bank.entities.account;

import com.ifgoiano.caixa2bank.entities.transaction.Transaction;
import com.ifgoiano.caixa2bank.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "num_seq")
    @SequenceGenerator(name = "num_seq", sequenceName = "number_seq", allocationSize = 1, initialValue = 100000)
    @Column(name="number", nullable=false, length = 6)
    private Integer number;

    @Column(name="password", nullable=false)
    private String password;

    @Column(name="balance", nullable = false, columnDefinition = "default 0.0")
    private BigDecimal balance;

    @Column(name="pix-random-key", nullable=true)
    private String pixRandomKey;

    @Column(name="pix-phone", nullable=true)
    private String pixPhone;

    @Column(name="pix-email", nullable=true)
    private String pixEmail;

    @Column(name="pix-cpf", nullable=true)
    private String pixCpf;

    @OneToOne
    User user;

    @OneToMany
    List<Transaction> transactions;

    public Account(String password, User user) {
        this.password = password;
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

    public List<Boolean> checkRegisteredKeys() {

        List<Boolean> registeredKeys = new ArrayList<Boolean>();

        if (this.getPixCpf() == null) {
            registeredKeys.add(0, true);
        } else {
            registeredKeys.add(0, false);
        }

        if (this.getPixRandomKey() == null) {
            registeredKeys.add(1, true);
        } else {
            registeredKeys.add(1, false);
        }

        if (this.getPixEmail() == null) {
            registeredKeys.add(2, true);
        } else {
            registeredKeys.add(2, false);
        }

        if (this.getPixPhone() == null) {
            registeredKeys.add(3, true);
        } else {
            registeredKeys.add(3, false);
        }


        return registeredKeys;
    }

}
