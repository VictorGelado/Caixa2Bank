package com.ifgoiano.caixa2bank.services.email;

import com.ifgoiano.caixa2bank.entities.account.Account;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine template;


    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendEmailForgotPassword(Account account, String code) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper =
                new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        Context context = new Context();

        context.setVariable("link", "http://localhost:8080/account/forgot-password?code=" + code);

        String html = template.process("emails/forgot-password-email", context);

        helper.setTo(account.getUser().getEmail());
        helper.setText(html, true);
        helper.setSubject("Alterar senha de acesso.");
        helper.setFrom("Caixa2Bank");

        mailSender.send(message);
    }
}
