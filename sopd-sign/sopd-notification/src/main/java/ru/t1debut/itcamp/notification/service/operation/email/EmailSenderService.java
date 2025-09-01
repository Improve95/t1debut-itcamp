package ru.t1debut.itcamp.notification.service.operation.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.notification.service.domain.EmailMessageDto;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailAlias;

    @Value("${spring.mail.replace-alias-on-from}")
    private boolean replaceAliasOnFrom;

    /**
     * Отправка одного письма (без вложений/inline/шаблонов).
     */
    public void send(EmailMessageDto messageDto)  {
        try {
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper h = new MimeMessageHelper(mime, StandardCharsets.UTF_8.name());

            if(replaceAliasOnFrom){
                h.setFrom(messageDto.from());
            }else{
                h.setFrom(emailAlias);
            }

            h.setTo(messageDto.to());
            h.setSubject(messageDto.title());
            h.setText(messageDto.body(), messageDto.isHtmlBody());

            mailSender.send(mime);
        } catch (MessagingException | MailException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}