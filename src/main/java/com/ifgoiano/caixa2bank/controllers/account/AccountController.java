package com.ifgoiano.caixa2bank.controllers.account;

import com.ifgoiano.caixa2bank.services.account.AccountService;
import com.ifgoiano.caixa2bank.services.account.KeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    KeysService keysService;

    @GetMapping("/pix")
    public ModelAndView getPagePix() {
        ModelAndView view = new ModelAndView("area-pix");

        List<String> keys = keysService.getKeysAccount();
        view.addObject("keys", keys);

        List<Boolean> registeredKeys = keysService.checkRegisteredKeys();
        view.addObject("checkKeys", registeredKeys);

        return view;
    }

    @GetMapping("/register-key/{key}")
    public ModelAndView registerKey(@PathVariable String key) {
        ModelAndView view = new ModelAndView("area-pix");

        accountService.saveKey(key);

        List<String> keys = keysService.getKeysAccount();
        view.addObject("keys", keys);

        List<Boolean> registeredKeys = keysService.checkRegisteredKeys();
        view.addObject("checkKeys", registeredKeys);

        return view;
    }

}