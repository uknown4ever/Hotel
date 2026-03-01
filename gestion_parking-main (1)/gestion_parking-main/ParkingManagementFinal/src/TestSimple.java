/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PC
 */
import java.sql.*;

public class TestSimple {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/parking_db";
        String user = "root";
        String password = "";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ CONNEXION RÉUSSIE !");
            
            // Vérifier si la table existe
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM utilisateur");
            rs.next();
            System.out.println("✅ Table utilisateur accessible");
            
            conn.close();
            
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver MySQL non trouvé !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Erreur de connexion SQL !");
            System.out.println("Message: " + e.getMessage());
            System.out.println("Code erreur: " + e.getErrorCode());
        }
    }
}
