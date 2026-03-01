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


import util.DBConnection;
import java.sql.*;
import java.util.*;

public class StatsDAO {
    
    private Connection conn = DBConnection.getConnection();
    
    /**
     * Calcule le taux d'occupation par mois
     * Retourne une liste de [mois, tauxOccupation]
     */
    public List<Object[]> getTauxOccupationParMois() {
        List<Object[]> resultats = new ArrayList<>();
        
        String sql = "SELECT " +
                     "DATE_FORMAT(dateEntree, '%Y-%m') as mois, " +
                     "SUM(TIMESTAMPDIFF(HOUR, dateEntree, IFNULL(dateSortie, NOW()))) as heuresOccupees, " +
                     "COUNT(DISTINCT numero) * 24 * DAY(LAST_DAY(dateEntree)) as heuresTotales " +
                     "FROM stationnement " +
                     "GROUP BY DATE_FORMAT(dateEntree, '%Y-%m') " +
                     "ORDER BY mois";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String mois = rs.getString("mois");
                double heuresOccupees = rs.getDouble("heuresOccupees");
                double heuresTotales = rs.getDouble("heuresTotales");
                
                double tauxOccupation = (heuresTotales > 0) ? 
                    (heuresOccupees / heuresTotales) * 100 : 0;
                
                resultats.add(new Object[]{mois, tauxOccupation});
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return resultats;
    }
    
    /**
     * Calcule les revenus par mois
     * Retourne une liste de [mois, revenus]
     */
    public List<Object[]> getRevenusParMois() {
        List<Object[]> resultats = new ArrayList<>();
        
        String sql = "SELECT " +
                     "DATE_FORMAT(dateSortie, '%Y-%m') as mois, " +
                     "SUM(montant) as totalRevenus " +
                     "FROM stationnement " +
                     "WHERE dateSortie IS NOT NULL " +
                     "GROUP BY DATE_FORMAT(dateSortie, '%Y-%m') " +
                     "ORDER BY mois";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String mois = rs.getString("mois");
                double revenus = rs.getDouble("totalRevenus");
                resultats.add(new Object[]{mois, revenus});
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return resultats;
    }
    
    /**
     * Calcule le taux d'occupation par type de place
     */
    public List<Object[]> getTauxOccupationParType() {
        List<Object[]> resultats = new ArrayList<>();
        
        String sql = "SELECT " +
                     "p.type, " +
                     "COUNT(s.id) as nbStationnements, " +
                     "SUM(TIMESTAMPDIFF(HOUR, s.dateEntree, IFNULL(s.dateSortie, NOW()))) as heuresOccupees " +
                     "FROM place p " +
                     "LEFT JOIN stationnement s ON p.numero = s.numero " +
                     "GROUP BY p.type";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String type = rs.getString("type");
                int nbStationnements = rs.getInt("nbStationnements");
                double heuresOccupees = rs.getDouble("heuresOccupees");
                
                resultats.add(new Object[]{type, nbStationnements, heuresOccupees});
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return resultats;
    }
    
    /**
     * Calcule les revenus par type de place
     */
    public List<Object[]> getRevenusParType() {
        List<Object[]> resultats = new ArrayList<>();
        
        String sql = "SELECT " +
                     "p.type, " +
                     "SUM(s.montant) as totalRevenus, " +
                     "COUNT(s.id) as nbStationnements " +
                     "FROM place p " +
                     "LEFT JOIN stationnement s ON p.numero = s.numero " +
                     "WHERE s.dateSortie IS NOT NULL " +
                     "GROUP BY p.type";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String type = rs.getString("type");
                double revenus = rs.getDouble("totalRevenus");
                int nbStationnements = rs.getInt("nbStationnements");
                
                resultats.add(new Object[]{type, revenus, nbStationnements});
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return resultats;
    }
}
