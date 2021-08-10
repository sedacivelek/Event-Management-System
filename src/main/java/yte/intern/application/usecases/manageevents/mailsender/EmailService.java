package yte.intern.application.usecases.manageevents.mailsender;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import yte.intern.application.usecases.manageevents.entity.Participant;
import yte.intern.application.usecases.manageevents.qrcodegen.QrCodeGenerator;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailService  {

    private final JavaMailSender mailSender;


    public void sendMail(Participant participant,String eventId) throws Exception
    {
        QrCodeGenerator qrCode = new QrCodeGenerator();
        String path=  qrCode.writeQRCode(participant,eventId);
        String cid = "qr";
        MimeMessage message = mailSender.createMimeMessage();
        Address address = new InternetAddress(participant.getEmail());
        message.setRecipient(Message.RecipientType.TO,address);
        message.setFrom("eventHeronoreply@gmail.com");
        message.setSubject("QR CODE FOR ENTRANCE");

        Multipart multipart = new MimeMultipart();

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.attachFile(path);
        mimeBodyPart.setContentID("<qr>");
        mimeBodyPart.setDisposition(MimeBodyPart.INLINE);

        MimeBodyPart mimeBodyTextPart = new MimeBodyPart();
        mimeBodyTextPart.setText("<html>" + "Hello " + participant.getNameAndSurname() + ", <br> "
                + "Please find your QR code for entrance to event with eventId:" + eventId + " <br> "
                + "<div><img src=\"cid:" + cid
                + "\" /></div></html>" + "Thanks & Regards, <br> "
                + "Event Hero Team </html>", "US-ASCII", "html");
        multipart.addBodyPart(mimeBodyTextPart);
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        mailSender.send(message);
        File file = new File(path);
        file.delete();
    }
}
