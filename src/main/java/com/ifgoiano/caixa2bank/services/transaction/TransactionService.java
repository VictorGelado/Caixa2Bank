package com.ifgoiano.caixa2bank.services.transaction;

import com.ifgoiano.caixa2bank.entities.account.Account;
import com.ifgoiano.caixa2bank.entities.transaction.ListTransactionDTO;
import com.ifgoiano.caixa2bank.entities.transaction.Transaction;
import com.ifgoiano.caixa2bank.entities.transaction.TransactionDTO;
import com.ifgoiano.caixa2bank.repository.TransactionRepository;
import com.ifgoiano.caixa2bank.services.account.AccountService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @SneakyThrows
    public void send(TransactionDTO tr) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal != null) {
            Account receiver = accountService.findByPix(tr.receiver());
            Account sender = accountService.findByLogin(principal.getUsername());

            if (receiver == sender) throw new Exception();

            if (receiver == null) receiver = accountService.findByNumberAccount(Integer.parseInt(tr.receiver()));

            //LocalDateTime lDate = LocalDateTime.parse(LocalDateTime.now().toString(), DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));

            if (sender.getBalance().compareTo(tr.value()) >= 0 &&
                    passwordEncoder().matches(tr.passwordTransaction(), sender.getPasswordTransaction())) {
                receiver.setBalance(receiver.getBalance().add(tr.value()));
                sender.setBalance(sender.getBalance().subtract(tr.value()));
            } else throw new Exception("");


            accountService.updateBalance(receiver.getNumber(), receiver.getBalance());
            accountService.updateBalance(sender.getNumber(), sender.getBalance());

            Transaction transaction = new Transaction(tr.value(), LocalDateTime.now(), sender, receiver);

            transactionRepository.save(transaction);
        }
    }

    public List<ListTransactionDTO> findAllTransactions(Account account) {
        List<Transaction> allTransactions = transactionRepository.findAllTransactions(account);
        List<ListTransactionDTO> transactions = new ArrayList<ListTransactionDTO>();

        allTransactions.forEach(t -> {
            String sender = t.getSender().getUser().getUsername();
            String receiver = t.getReceiver().getUser().getUsername();

            if (account.getUser().getCpf().equals(t.getSender().getUser().getCpf())) sender = "Você";
            else if (account.getUser().getCpf().equals(t.getReceiver().getUser().getCpf())) receiver = "Você";

            DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            String hour = t.getTime().format(hourFormatter);
            String date = t.getTime().format(dateFormatter);

            String dateTime = date + " às " + hour;

            ListTransactionDTO transaction = new ListTransactionDTO(
                    t.getId(),
                    sender,
                    t.getValue(),
                    receiver,
                    dateTime
            );

            transactions.add(transaction);
        });

        return transactions;
    }
}
