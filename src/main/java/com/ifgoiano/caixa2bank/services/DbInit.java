package com.ifgoiano.caixa2bank.services;

import com.ifgoiano.caixa2bank.entities.user.Authority;
import com.ifgoiano.caixa2bank.entities.user.User;
import com.ifgoiano.caixa2bank.entities.user.UserAdminDTO;
import com.ifgoiano.caixa2bank.services.authority.AuthorityService;
import com.ifgoiano.caixa2bank.services.user.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DbInit {

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void createAuthorities() {
        Authority authorityAdmin = new Authority(1L, "admin");
        Authority authorityUser = new Authority(2L, "user");

        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityAdmin);
        authorities.add(authorityUser);

       // if (authorityService.findById(1L).getName() == null && authorityService.findById(2L).getName() == null) {
            authorityService.saveAllAuthority(authorities);
       // }
    }

    @PostConstruct
    public void createAdmin() {
        UserAdminDTO newUser = new UserAdminDTO("admin", "admin", "00000000011",
                "admin@gmail.com", "(62)900000000"
        );

        if (userService.findByLogin(newUser.cpf()) == null) {
            User user = new User(newUser);

            userService.saveAdmin(user);
        }
    }
}