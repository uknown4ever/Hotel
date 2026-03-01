/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

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


import dao.StatsDAO;
import java.util.List;

public class TestStats {
    public static void main(String[] args) {
        System.out.println("=== TEST STATISTIQUES ===\n");
        
        StatsDAO statsDAO = new StatsDAO();
        
        // 1. Test taux d'occupation par mois
        System.out.println("1. Taux d'occupation par mois :");
        List<Object[]> taux = statsDAO.getTauxOccupationParMois();
        if (taux.isEmpty()) {
            System.out.println("   ⚠ Aucune donnée trouvée");
            System.out.println("   → Ajoutez des stationnements pour voir des résultats");
        } else {
            for (Object[] row : taux) {
                System.out.println("   " + row[0] + " : " + String.format("%.2f", row[1]) + "%");
            }
        }
        
        // 2. Test revenus par mois
        System.out.println("\n2. Revenus par mois :");
        List<Object[]> revenus = statsDAO.getRevenusParMois();
        if (revenus.isEmpty()) {
            System.out.println("   ⚠ Aucune donnée trouvée");
            System.out.println("   → Ajoutez des stationnements avec sortie pour voir des résultats");
        } else {
            for (Object[] row : revenus) {
                System.out.println("   " + row[0] + " : " + String.format("%.2f", row[1]) + " DH");
            }
        }
        
        // 3. Test taux par type
        System.out.println("\n3. Taux d'occupation par type :");
        List<Object[]> tauxType = statsDAO.getTauxOccupationParType();
        if (tauxType.isEmpty()) {
            System.out.println("   ⚠ Aucune donnée trouvée");
        } else {
            for (Object[] row : tauxType) {
                System.out.println("   " + row[0] + " : " + row[2] + " heures");
            }
        }
        
        // 4. Test revenus par type
        System.out.println("\n4. Revenus par type :");
        List<Object[]> revenusType = statsDAO.getRevenusParType();
        if (revenusType.isEmpty()) {
            System.out.println("   ⚠ Aucune donnée trouvée");
        } else {
            for (Object[] row : revenusType) {
                System.out.println("   " + row[0] + " : " + String.format("%.2f", row[1]) + " DH");
            }
        }
        
        System.out.println("\n=== FIN DU TEST ===");
    }
}