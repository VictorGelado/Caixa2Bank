package com.ifgoiano.caixa2bank.controllers.account;

import com.ifgoiano.caixa2bank.entities.account.Account;
import com.ifgoiano.caixa2bank.entities.account.KeysExistisDTO;
import com.ifgoiano.caixa2bank.services.account.AccountService;
import com.ifgoiano.caixa2bank.services.account.KeysService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

        KeysExistisDTO registeredKeys = keysService.checkRegisteredKeys();
        view.addObject("checkKeys", registeredKeys);

        return view;
    }

    @GetMapping("/register-key/{key}")
    public ModelAndView registerKey(@PathVariable String key) {
        ModelAndView view = new ModelAndView("area-pix");

        accountService.saveKey(key);

        List<String> keys = keysService.getKeysAccount();
        view.addObject("keys", keys);

        KeysExistisDTO registeredKeys = keysService.checkRegisteredKeys();
        view.addObject("checkKeys", registeredKeys);

        return view;
    }

    @GetMapping("/forgot-password")
    public String getForgotPasswordPage(ModelMap mM, @RequestParam(value = "code", required = false) String code) {
        Account account = null;

        if (code != null) {
             byte[] bytesDecode = Base64.getDecoder().decode(code.getBytes(StandardCharsets.UTF_8));
             String login = new String(bytesDecode, StandardCharsets.UTF_8);

            account = accountService.findByLogin(login);

            account.setPassword("");
        }

        mM.addAttribute("account", account);

        return "forgot-password";
    }

    @GetMapping("/email-forgot-password")
    public String sendEmailForgot(@RequestParam("login") String login) throws MessagingException {
        accountService.sendEmailForgotPassword(login);

        return "redirect:/user/login";
    }

    @PostMapping("/forgot-password")
    public String getForgotPasswordPage(Account account) {
        accountService.updatePassword(account, true);

        if (account.getPassword().trim().equals("")) return "redirect:/error";

        return "redirect:/user/login";
    }

}
