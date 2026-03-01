/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

/**
 *
 * @author PC
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import dao.StatsDAO;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;
import org.jfree.data.general.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphiqueFrame extends javax.swing.JInternalFrame {
    
    private StatsDAO statsDAO;
    private JPanel panelGraphique;
    private JComboBox<String> comboTypeGraphique;
    private JComboBox<String> comboPeriode;
    private JButton btnActualiser;
    
    public GraphiqueFrame() {
        statsDAO = new StatsDAO();
        initComponents();
        chargerGraphique("occupation", "mois");
    }
    
    private void initComponents() {
        setTitle("Statistiques - Graphiques");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(800, 600);
        
        // Panneau de contrôle
        JPanel panelControle = new JPanel(new FlowLayout());
        panelControle.setBackground(new Color(255, 204, 204));
        
        panelControle.add(new JLabel("Type de graphique:"));
        comboTypeGraphique = new JComboBox<>(new String[]{
            "Taux d'occupation par mois",
            "Revenus par mois",
            "Taux d'occupation par type",
            "Revenus par type"
        });
        panelControle.add(comboTypeGraphique);
        
        panelControle.add(new JLabel("Période:"));
        comboPeriode = new JComboBox<>(new String[]{
            "Par mois",
            "Par trimestre",
            "Par année"
        });
        panelControle.add(comboPeriode);
        
        btnActualiser = new JButton("Actualiser");
        btnActualiser.setBackground(Color.BLACK);
        btnActualiser.setForeground(new Color(255, 204, 204));
        panelControle.add(btnActualiser);
        
        // Panneau pour le graphique
        panelGraphique = new JPanel(new BorderLayout());
        panelGraphique.setBackground(Color.WHITE);
        
        // Ajouter les composants
        setLayout(new BorderLayout());
        add(panelControle, BorderLayout.NORTH);
        add(panelGraphique, BorderLayout.CENTER);
        
        // Action du bouton
        btnActualiser.addActionListener(e -> {
            int type = comboTypeGraphique.getSelectedIndex();
            String periode = "mois"; // Simplifié pour l'exemple
            switch (type) {
                case 0: chargerGraphique("occupation", periode); break;
                case 1: chargerGraphique("revenus", periode); break;
                case 2: chargerGraphique("occupationType", periode); break;
                case 3: chargerGraphique("revenusType", periode); break;
            }
        });
    }
    
    private void chargerGraphique(String type, String periode) {
        panelGraphique.removeAll();
        
        JFreeChart chart = null;
        
        switch (type) {
            case "occupation":
                chart = createTauxOccupationChart();
                break;
            case "revenus":
                chart = createRevenusChart();
                break;
            case "occupationType":
                chart = createTauxOccupationTypeChart();
                break;
            case "revenusType":
                chart = createRevenusTypeChart();
                break;
        }
        
        if (chart != null) {
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(750, 500));
            panelGraphique.add(chartPanel, BorderLayout.CENTER);
        }
        
        panelGraphique.revalidate();
        panelGraphique.repaint();
    }
    
    private JFreeChart createTauxOccupationChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        List<Object[]> donnees = statsDAO.getTauxOccupationParMois();
        
        for (Object[] row : donnees) {
            String mois = (String) row[0];
            double taux = (double) row[1];
            dataset.addValue(taux, "Taux d'occupation (%)", mois);
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Taux d'occupation par mois",
            "Mois",
            "Taux (%)",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );
        
        return chart;
    }
    
    private JFreeChart createRevenusChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        List<Object[]> donnees = statsDAO.getRevenusParMois();
        
        for (Object[] row : donnees) {
            String mois = (String) row[0];
            double revenus = (double) row[1];
            dataset.addValue(revenus, "Revenus (DH)", mois);
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Revenus par mois",
            "Mois",
            "Revenus (DH)",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );
        
        return chart;
    }
    
    private JFreeChart createTauxOccupationTypeChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        List<Object[]> donnees = statsDAO.getTauxOccupationParType();
        
        for (Object[] row : donnees) {
            String type = (String) row[0];
            double heures = (double) row[2];
            dataset.setValue(type + " (" + (int)heures + " h)", heures);
        }
        
        JFreeChart chart = ChartFactory.createPieChart(
            "Taux d'occupation par type de place",
            dataset,
            true, true, false
        );
        
        return chart;
    }
    
    private JFreeChart createRevenusTypeChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        List<Object[]> donnees = statsDAO.getRevenusParType();
        
        for (Object[] row : donnees) {
            String type = (String) row[0];
            double revenus = (double) row[1];
            dataset.setValue(type + " (" + (int)revenus + " DH)", revenus);
        }
        
        JFreeChart chart = ChartFactory.createPieChart(
            "Revenus par type de place",
            dataset,
            true, true, false
        );
        
        return chart;
    }
}