/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PC
 */

import dao.UtilisateurDAO;
import entites.Utilisateur;

public class Test {
    public static void main(String[] args) {
        System.out.println("=== CRÉATION DES UTILISATEURS ===\n");
        
        UtilisateurDAO dao = new UtilisateurDAO();
        
        // Créer admin
        Utilisateur admin = new Utilisateur();
        admin.setNomUtilisateur("admin");
        admin.setRole("admin");
        admin.setEmail("admin@parking.com");
        
        boolean ok = dao.ajouterUtilisateur(admin, "admin123");
        System.out.println(ok ? "✅ Admin créé (admin/admin123)" : "❌ Admin existe déjà");
        
        // Créer utilisateur normal
        Utilisateur user = new Utilisateur();
        user.setNomUtilisateur("user");
        user.setRole("user");
        user.setEmail("user@parking.com");
        
        ok = dao.ajouterUtilisateur(user, "user123");
        System.out.println(ok ? "✅ User créé (user/user123)" : "❌ User existe déjà");
        
        System.out.println("\n=== FIN ===");
    }
}