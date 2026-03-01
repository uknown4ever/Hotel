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



import entites.Place;
import entites.Stationnement;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StationnementDAO implements StationnementDAOInterface {

    Connection conn = DBConnection.getConnection();


    
    // ENTREE VEHICULE
    

    @Override
    
   public boolean create(Stationnement s){

    try{

        Connection conn = DBConnection.getConnection();

        // 1 verifier si place libre

        String check = "SELECT statut FROM place WHERE numero=?";

        PreparedStatement psCheck = conn.prepareStatement(check);

        psCheck.setInt(1, s.getNumero());

        ResultSet rs = psCheck.executeQuery();

        if(rs.next()){

            String statut = rs.getString("statut");

            if(statut.equals("Occupee")){

                System.out.println("Place deja occupée !");

                return false;

            }
        } else {
                System.out.println("Place inexistante !");
                return false;
            }
        


        // 2 inserer stationnement

        String sql = "INSERT INTO stationnement(numero, matricule, dateEntree) VALUES(?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, s.getNumero());

        ps.setString(2, s.getMatricule());

        ps.setTimestamp(3, new java.sql.Timestamp(s.getDateEntree().getTime()));

        ps.executeUpdate();


        // 3 changer statut place

        String update = "UPDATE place SET statut='Occupee' WHERE numero=?";

        PreparedStatement ps2 = conn.prepareStatement(update);

        ps2.setInt(1, s.getNumero());

        ps2.executeUpdate();


        System.out.println("Stationnement ajouté");

        return true;

    }

    catch(Exception e){

        e.printStackTrace();

    }

    return false;

}

    
    // SORTIE VEHICULE
    

      @Override
    public void update(Stationnement s) {
        try {
            // Récupérer la date d'entrée et le numéro de place
            String sql = "SELECT dateEntree, numero FROM stationnement WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, s.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Timestamp dateEntree = rs.getTimestamp("dateEntree");
                int numeroPlace = rs.getInt("numero");

                // Récupérer le tarif horaire de la place
                String sqlTarif = "SELECT tarifHoraire FROM place WHERE numero=?";
                PreparedStatement psTarif = conn.prepareStatement(sqlTarif);
                psTarif.setInt(1, numeroPlace);
                ResultSet rsTarif = psTarif.executeQuery();

                if (rsTarif.next()) {
                    double tarifHoraire = rsTarif.getDouble("tarifHoraire");
                    
                    // Calculer la durée et le montant
                    Timestamp dateSortie = new Timestamp(new Date().getTime());
                    long diff = dateSortie.getTime() - dateEntree.getTime();
                    double heures = diff / (1000.0 * 60 * 60);
                    
                    // Arrondir à 2 décimales
                    double montant = Math.round((heures * tarifHoraire) * 100.0) / 100.0;

                    // Mettre à jour le stationnement avec dateSortie et montant
                    String update = "UPDATE stationnement SET dateSortie=?, montant=? WHERE id=?";
                    PreparedStatement ps2 = conn.prepareStatement(update);
                    ps2.setTimestamp(1, dateSortie);
                    ps2.setDouble(2, montant);
                    ps2.setInt(3, s.getId());
                    ps2.executeUpdate();

                    // Libérer la place
                    String liberer = "UPDATE place SET statut='Libre' WHERE numero=?";
                    PreparedStatement ps3 = conn.prepareStatement(liberer);
                    ps3.setInt(1, numeroPlace);
                    ps3.executeUpdate();

                    System.out.println("Sortie enregistrée - Montant: " + montant + " DH");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            // Avant de supprimer, libérer la place si elle était occupée
            String select = "SELECT numero FROM stationnement WHERE id=? AND dateSortie IS NULL";
            PreparedStatement psSelect = conn.prepareStatement(select);
            psSelect.setInt(1, id);
            ResultSet rs = psSelect.executeQuery();
            
            if (rs.next()) {
                int numeroPlace = rs.getInt("numero");
                String liberer = "UPDATE place SET statut='Libre' WHERE numero=?";
                PreparedStatement psLiberer = conn.prepareStatement(liberer);
                psLiberer.setInt(1, numeroPlace);
                psLiberer.executeUpdate();
            }

            String sql = "DELETE FROM stationnement WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Stationnement findById(int id) {
        Stationnement s = null;
        try {
            String sql = "SELECT * FROM stationnement WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                s = new Stationnement();
                s.setId(rs.getInt("id"));
                s.setNumero(rs.getInt("numero"));
                s.setMatricule(rs.getString("matricule"));
                s.setDateEntree(rs.getTimestamp("dateEntree"));
                s.setDateSortie(rs.getTimestamp("dateSortie"));
                s.setMontant(rs.getDouble("montant"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public List<Stationnement> findAll() {
        List<Stationnement> list = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM stationnement ORDER BY dateEntree DESC");

            while (rs.next()) {
                Stationnement s = new Stationnement();
                s.setId(rs.getInt("id"));
                s.setNumero(rs.getInt("numero"));
                s.setMatricule(rs.getString("matricule"));
                s.setDateEntree(rs.getTimestamp("dateEntree"));
                s.setDateSortie(rs.getTimestamp("dateSortie"));
                s.setMontant(rs.getDouble("montant"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Méthode supplémentaire pour trouver les stationnements par véhicule
    public List<Stationnement> findByMatricule(String matricule) {
        List<Stationnement> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM stationnement WHERE matricule=? ORDER BY dateEntree DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, matricule);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Stationnement s = new Stationnement();
                s.setId(rs.getInt("id"));
                s.setNumero(rs.getInt("numero"));
                s.setMatricule(rs.getString("matricule"));
                s.setDateEntree(rs.getTimestamp("dateEntree"));
                s.setDateSortie(rs.getTimestamp("dateSortie"));
                s.setMontant(rs.getDouble("montant"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Stationnement> findByTypePlace(String type) {
            List<Stationnement> liste = new ArrayList<>();
            // Jointure car le 'type' est dans la table 'place' et non 'stationnement'
            String sql = "SELECT s.* FROM stationnement s JOIN place p ON s.numero = p.numero WHERE p.type = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, type);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Stationnement s = new Stationnement(
                        rs.getInt("numero"), rs.getString("matricule"),
                        rs.getTimestamp("dateEntree"), rs.getTimestamp("dateSortie"),
                        rs.getDouble("montant")
                    );
                    s.setId(rs.getInt("id"));
                    liste.add(s);
                }
                } catch (SQLException e) { e.printStackTrace(); }
                return liste;
    }

    public List<Stationnement> findByDateRange(Date debut, Date fin) {
        List<Stationnement> liste = new ArrayList<>();
        String sql = "SELECT * FROM stationnement WHERE dateEntree BETWEEN ? AND ?";

        try (Connection conn = util.DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Convertir java.util.Date en java.sql.Date pour SQL
            ps.setDate(1, new java.sql.Date(debut.getTime()));
            ps.setDate(2, new java.sql.Date(fin.getTime()));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Stationnement s = new Stationnement();
                s.setId(rs.getInt("id"));
                s.setNumero(Integer.parseInt(rs.getString("numero")));
                s.setMatricule(rs.getString("matricule"));
                s.setDateEntree(rs.getTimestamp("dateEntree"));
                s.setDateSortie(rs.getTimestamp("dateSortie"));
                s.setMontant(rs.getDouble("montant"));
                liste.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
    
    public List<Object[]> findPlacesSimples(String statut) {
        List<Object[]> liste = new ArrayList<>();
        // Requête simple sur la table place
        String sql = "SELECT numero, type, tarifHoraire FROM place WHERE statut = ?";

        try (Connection conn = util.DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, statut); // "Libre" ou "Occupée"
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                liste.add(new Object[]{
                    rs.getString("numero"),
                    rs.getString("type"),
                    rs.getDouble("tarifHoraire")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
    
}