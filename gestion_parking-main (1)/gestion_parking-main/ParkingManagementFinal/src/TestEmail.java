/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PC
 */
import service.EmailService;

public class TestEmail {
    public static void main(String[] args) {
        System.out.println("=== TEST ENVOI D'EMAIL ===\n");
        
        // Remplacez par VOTRE adresse email (celle où vous voulez recevoir le test)
        String monEmail = "basmaaitlechgueur@gmail.com";  // CHANGEZ ICI !!!
        
        boolean envoye = EmailService.envoyerEmailReinitialisation(
            monEmail,     // destinataire
            "123456",     // code factice
            "Admin"       // nom
        );
        
        if (envoye) {
            System.out.println("✅ Email envoyé ! Vérifiez votre boîte.");
        } else {
            System.out.println("❌ Échec de l'envoi.");
        }
    }
}
