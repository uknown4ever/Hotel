/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author PC
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import entites.Utilisateur;
import util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.time.LocalDateTime;

public class UtilisateurDAO implements UtilisateurDAOInterface {
    
    private Connection conn = DBConnection.getConnection();
    
    @Override
    public boolean ajouterUtilisateur(Utilisateur utilisateur, String motDePasseClair) {
        try {
            if (utilisateurExiste(utilisateur.getNomUtilisateur())) {
                System.out.println("Nom d'utilisateur déjà pris");
                return false;
            }
            
            // Générer le hash du mot de passe
            String hash = BCrypt.hashpw(motDePasseClair, BCrypt.gensalt(12));
            
            String sql = "INSERT INTO utilisateur (nomUtilisateur, motDePasse, role, email) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, utilisateur.getNomUtilisateur());
            ps.setString(2, hash);
            ps.setString(3, utilisateur.getRole() != null ? utilisateur.getRole() : "user");
            ps.setString(4, utilisateur.getEmail());
            
            int rows = ps.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Utilisateur authentifier(String nomUtilisateur, String motDePasseClair) {
        try {
            String sql = "SELECT * FROM utilisateur WHERE nomUtilisateur = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nomUtilisateur);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String hashStocke = rs.getString("motDePasse");
                
                // Vérifier le mot de passe avec BCrypt
                if (BCrypt.checkpw(motDePasseClair, hashStocke)) {
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setId(rs.getInt("id"));
                    utilisateur.setNomUtilisateur(rs.getString("nomUtilisateur"));
                    utilisateur.setMotDePasse(hashStocke);
                    utilisateur.setRole(rs.getString("role"));
                    utilisateur.setEmail(rs.getString("email"));
                    utilisateur.setDateCreation(rs.getString("dateCreation"));
                    
                    System.out.println("Authentification réussie pour: " + nomUtilisateur);
                    return utilisateur;
                } else {
                    System.out.println("Mot de passe incorrect");
                }
            } else {
                System.out.println("Utilisateur non trouvé: " + nomUtilisateur);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // ==================== MÉTHODES POUR LA RÉCUPÉRATION DE MOT DE PASSE ====================
    
    /**
     * Génère et enregistre un token de réinitialisation pour un utilisateur
     */
    public String genererTokenReinitialisation(String email) {
        try {
            String sqlCheck = "SELECT id, nomUtilisateur FROM utilisateur WHERE email = ?";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setString(1, email);
            ResultSet rs = psCheck.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                String nomUtilisateur = rs.getString("nomUtilisateur");
                
                // Générer un token aléatoire à 6 chiffres
                String token = String.format("%06d", (int)(Math.random() * 1000000));
                
                // Définir l'expiration (15 minutes)
                LocalDateTime expiry = LocalDateTime.now().plusMinutes(15);
                Timestamp expiryTimestamp = Timestamp.valueOf(expiry);
                
                String sqlUpdate = "UPDATE utilisateur SET reset_token = ?, reset_token_expiry = ? WHERE id = ?";
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setString(1, token);
                psUpdate.setTimestamp(2, expiryTimestamp);
                psUpdate.setInt(3, id);
                psUpdate.executeUpdate();
                
                // Envoyer l'email (vous devez créer la classe EmailService)
                boolean emailEnvoye = service.EmailService.envoyerEmailReinitialisation(email, token, nomUtilisateur);
                
                if (emailEnvoye) {
                    return token;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Vérifie si un token est valide et non expiré
     */
    public boolean verifierToken(String token) {
        try {
            String sql = "SELECT id FROM utilisateur WHERE reset_token = ? AND reset_token_expiry > ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, token);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            
            ResultSet rs = ps.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Réinitialise le mot de passe avec un token valide
     */
    public boolean reinitialiserMotDePasse(String token, String nouveauMotDePasse) {
        try {
            String sqlCheck = "SELECT id FROM utilisateur WHERE reset_token = ? AND reset_token_expiry > ?";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setString(1, token);
            psCheck.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            
            ResultSet rs = psCheck.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                
                // Générer le nouveau hash
                String nouveauHash = BCrypt.hashpw(nouveauMotDePasse, BCrypt.gensalt(12));
                
                String sqlUpdate = "UPDATE utilisateur SET motDePasse = ?, reset_token = NULL, reset_token_expiry = NULL WHERE id = ?";
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setString(1, nouveauHash);
                psUpdate.setInt(2, id);
                
                int rows = psUpdate.executeUpdate();
                return rows > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Récupère l'email d'un utilisateur par son nom
     */
    public String getEmailByUsername(String nomUtilisateur) {
        try {
            String sql = "SELECT email FROM utilisateur WHERE nomUtilisateur = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nomUtilisateur);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // ==================== AUTRES MÉTHODES ====================
    
    @Override
    public boolean modifierMotDePasse(int id, String ancienMotDePasse, String nouveauMotDePasse) {
        try {
            String sql = "SELECT motDePasse FROM utilisateur WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String hashAncien = rs.getString("motDePasse");
                
                if (BCrypt.checkpw(ancienMotDePasse, hashAncien)) {
                    String nouveauHash = BCrypt.hashpw(nouveauMotDePasse, BCrypt.gensalt(12));
                    
                    String update = "UPDATE utilisateur SET motDePasse = ? WHERE id = ?";
                    PreparedStatement psUpdate = conn.prepareStatement(update);
                    psUpdate.setString(1, nouveauHash);
                    psUpdate.setInt(2, id);
                    
                    int rows = psUpdate.executeUpdate();
                    return rows > 0;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean supprimerUtilisateur(int id) {
        try {
            String sql = "DELETE FROM utilisateur WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            int rows = ps.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean utilisateurExiste(String nomUtilisateur) {
        try {
            String sql = "SELECT COUNT(*) FROM utilisateur WHERE nomUtilisateur = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nomUtilisateur);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static String genererHash(String motDePasse) {
        return BCrypt.hashpw(motDePasse, BCrypt.gensalt(12));
    }
}
