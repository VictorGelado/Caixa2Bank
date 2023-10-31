package com.ifgoiano.caixa2bank.entities.transaction;

import com.ifgoiano.caixa2bank.entities.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, length=6)
    private Long id;

    @Column(name="value", nullable = false, columnDefinition="numeric(20,2)")
    private BigDecimal value;

    //@Column(columnDefinition="timestamp(6) without time zone")
    //@CreationTimestamp
    //@Temporal(TemporalType.TIMESTAMP)
   /* @Column(updatable = false)
    private Date time;*/

    /*@Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Temporal(TemporalType.TIME)
    @NotNull
    private Date time;*/

    @ManyToOne
    @JoinColumn(name="sender_user_cpf")
    @NotNull
    private Account sender;

    @ManyToOne
    @JoinColumn(name="receiver_user_cpf")
    @NotNull
    private Account receiver;

    public Transaction(BigDecimal value, /*Date time,*/ Account sender, Account receiver) {
        this.value = value;
       // this.time = time;
        this.sender = sender;
        this.receiver = receiver;
    }
}
