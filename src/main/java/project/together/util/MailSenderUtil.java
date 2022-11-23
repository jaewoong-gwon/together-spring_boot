package project.together.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import project.together.vo.Mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailSenderUtil {

    private final String FROM_ADDRESS;
    private final JavaMailSender mailSender;

    public MailSenderUtil(@Value("${spring.mail.username}") String address, JavaMailSender mailSender) {
        this.FROM_ADDRESS = address;
        this.mailSender = mailSender;
    }

    public void sendMail(Mail mail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        // 메일을 보내는 Naver Mail
        messageHelper.setFrom(FROM_ADDRESS);
        // 받는사람 Email
        messageHelper.setTo(mail.getAddress());
        // 메일 제목
        messageHelper.setSubject(mail.getTitle());
        // 메일 내용
        messageHelper.setText(mail.getMessage());

        mailSender.send(mimeMessage);
    }
}
