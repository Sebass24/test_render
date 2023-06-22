package com.example.buensabor.Services.Email;

import com.example.buensabor.Models.Entity.Review;
import com.example.buensabor.Models.Entity.User;
import com.example.buensabor.Services.Impl.Auth0Service;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class MailService {
    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;
    @Value("${sendgrid.sender}")
    private String sender;


    public void testEmail(){
        sendEmail("emichiofalo@gmail.com","Hola","Hola Carola!..... CARA DE PISTOLAAAA!!!",null);
    }

    public void sendBill(String to){
        String content = "Gracias por su compra!! <br>"+
                "Le adjuntamos su factura";
        String subject = "[Buen Sabor] Factura";
        String attachmentPath = new File("").getAbsolutePath() + "/src/main/resources/Temp/" + "factura.pdf";

        sendEmail(to,subject,content,attachmentPath);
    }

    public void sendEmail(String to, String subject, String content, String attachmentPath) {
        Email from = new Email(sender);
        Email toEmail = new Email(to);
        Content messageContent = new Content("text/html", content);
        Mail mail = new Mail(from, subject, toEmail, messageContent);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            if (attachmentPath != null) {
                Attachments attachments = new Attachments();
                attachments.setFilename("factura.pdf");
                attachments.setType("application/pdf");
                attachments.setDisposition("attachment");
                attachments.setContent(Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(attachmentPath))));
                mail.addAttachments(attachments);
            }

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String sendReview(Review review){
        String subject = review.getName() + " envió una reseña";
        String content = review.getEmail() +" <br> " +review.getMessage();
        sendEmail(sender,subject,content,null);
        return "el mail fue enviado con exito";
    }

    public String sendPasswordTicket(String userEmail, String link){
        String subject ="[Buen Sabor] Creación de usuario";
        String content = "¡Bienvenido al equipo!<br>" +
                "<br>" +
                "Hemos creado tu cuenta de empleado del restaurante. <br>" +
                "<br>" +
                "Tu contraseña predeterminada es: <br>" +
                "<br>" +
                "Buensa1234<br>" +
                "<br>" +
                "Te recomendamos cambiar la contraseña desde el siguiente link: <br>" +
                "<br>" +
                "<a href= \""+link+"\"> Cambiar contraseña </a><br>" +
                "<br>" +
                "Saludos,<br>" +
                "El buen sabor";
        sendEmail(userEmail,subject,content,null);
        return "el mail fue enviado con exito";
    }

}
