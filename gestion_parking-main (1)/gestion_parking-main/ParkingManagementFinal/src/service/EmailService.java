/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author PC
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
    
    // CONFIGURATION GMAIL - À MODIFIER AVEC VOS IDENTIFIANTS
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_EXPEDITEUR = "basmaaitlechgueur@gmail.com";  
    private static final String MOT_DE_PASSE = "xyvl liya jcly stft";     
    
    /**
     * Envoie un email de réinitialisation de mot de passe
     */
    public static boolean envoyerEmailReinitialisation(String destinataire, String token, String nomUtilisateur) {
        
        // Configuration des propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        
        // Création de la session avec authentification
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_EXPEDITEUR, MOT_DE_PASSE);
            }
        });
        
        try {
            // Créer le message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_EXPEDITEUR));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject("Récupération de mot de passe - Gestion de Parking");
            
            // Contenu HTML de l'email
            String contenuHTML = genererEmailHTML(token, nomUtilisateur);
            message.setContent(contenuHTML, "text/html; charset=utf-8");
            
            // Envoyer
            Transport.send((Message) (Message) message);
            System.out.println("Email envoyé avec succès à " + destinataire);
            return true;
            
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Génère le contenu HTML de l'email
     */
    private static String genererEmailHTML(String token, String nomUtilisateur) {
        return "<!DOCTYPE html>" +
               "<html>" +
               "<head>" +
               "<style>" +
               "body { font-family: Arial, sans-serif; background-color: #f5f5f5; }" +
               ".container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: white; border-radius: 10px; }" +
               ".header { background-color: #FF9999; color: white; padding: 10px; text-align: center; border-radius: 5px; }" +
               ".content { padding: 20px; }" +
               ".code { font-size: 32px; font-weight: bold; color: #FF9999; text-align: center; padding: 20px; " +
               "background-color: #f0f0f0; border-radius: 5px; letter-spacing: 5px; }" +
               ".footer { text-align: center; color: #888; font-size: 12px; }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<div class='container'>" +
               "<div class='header'>" +
               "<h2>Gestion de Parking - Récupération de mot de passe</h2>" +
               "</div>" +
               "<div class='content'>" +
               "<p>Bonjour <strong>" + nomUtilisateur + "</strong>,</p>" +
               "<p>Vous avez demandé la réinitialisation de votre mot de passe.</p>" +
               "<p>Voici votre code de récupération à 6 chiffres :</p>" +
               "<div class='code'>" + token + "</div>" +
               "<p>Ce code est valable pendant <strong>15 minutes</strong>.</p>" +
               "<p>Si vous n'avez pas demandé cette réinitialisation, ignorez cet email.</p>" +
               "</div>" +
               "<div class='footer'>" +
               "<p>Cet email est automatique, merci de ne pas y répondre.</p>" +
               "</div>" +
               "</div>" +
               "</body>" +
               "</html>";
    }
}
