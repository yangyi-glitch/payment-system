package mav.shan.payment.service.mail;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
public class SendMailServiceImpl implements SendMailService {

    public static boolean sendQQEmailWithHtml(String fromEmail, String authorizationCode,
                                              String toEmail, String subject, String htmlContent) {
        try {
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.host", "smtp.qq.com");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, authorizationCode);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, toEmail);
            message.setSubject(subject);

            // 设置HTML内容
            message.setContent(htmlContent, "text/html;charset=UTF-8");

            Transport.send(message);

            System.out.println("HTML邮件发送成功！");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("HTML邮件发送失败：" + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        // 基本HTML表格样式和结构
        StringBuilder htmlBuilder = new StringBuilder("傻儿");

        sendQQEmailWithHtml("1965997927@qq.com",
                "cmfbosoonmcucccg",
                "994661254@qq.com",
                "傻儿",
                htmlBuilder.toString());
    }
}
