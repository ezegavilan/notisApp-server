package com.gavilan.sistemanotificacionesdemo.model.services.impl;

import com.gavilan.sistemanotificacionesdemo.model.dto.NotificacionEmail;
import com.gavilan.sistemanotificacionesdemo.model.exceptions.MailSenderException;
import com.gavilan.sistemanotificacionesdemo.model.services.MailContentBuilder;
import com.gavilan.sistemanotificacionesdemo.model.services.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    @Override
    @Async
    public void sendEmail(NotificacionEmail notificacionEmail, String url) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("deofis.mailsender-598aee@inbox.mailtrap.io");
            messageHelper.setTo(notificacionEmail.getRecipient());
            messageHelper.setSubject(notificacionEmail.getSubject());
            messageHelper.setText(this.mailContentBuilder.build(notificacionEmail.getBody(), url), true);
        };

        try {
            this.javaMailSender.send(messagePreparator);
            log.info("Email SENT");
        } catch (MailException e) {
            throw new MailSenderException("Excepción al enviar email a: ".concat(notificacionEmail.getRecipient()));
        }
    }
}
