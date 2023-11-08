package com.ifgoiano.caixa2bank.repository;

import com.ifgoiano.caixa2bank.entities.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Integer> {


    //select (password) from account  where account like 'jose' OR user_cpf like '123456789012'
    //@Query("select u from Account u where u.number = :account OR u.user.cpf like :cpf")
   // Account findByAccountNumberOrCpf(@Param("userId") String cpf,  @Param("cpf") int account);

   // @Query("SELECT a FROM Account a WHERE a.user.id = :userId OR a.cpf = :cpf")
   // List<Account> findByUserIdOrCpf(@Param("userId") Long userId, @Param("cpf") String cpf);

    Account findByNumber(int account);

    @Query("select u from Account u where u.user.cpf like :cpf")
    Account findByCpf(String cpf);

    @Query("select u from Account u where u.number = :number")
    Account findByNumberAccount(int number);

    @Query("select u from Account u where u.user.email like :email")
    Account findByEmail(String email);


//u.number = :key OR
    @Query("select u from Account u where u.pixRandomKey like :key " +
            "OR u.pixCpf like :key OR u.pixEmail like :key OR u.pixPhone like :key")
    Account findByPix(String key);
}
