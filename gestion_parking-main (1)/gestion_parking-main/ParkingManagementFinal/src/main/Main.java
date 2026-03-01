/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dao.PlaceDAO;
import dao.VehiculeDAO;
import dao.StationnementDAO;

import entites.Place;
import entites.Vehicule;
import entites.Stationnement;

import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        
         System.setProperty("sun.java2d.d3d", "false");
    System.setProperty("sun.java2d.opengl", "false");

        System.out.println("===== TEST PROJET PARKING =====");

     
       
  
        
        PlaceDAO placeDAO = new PlaceDAO();
        VehiculeDAO vehiculeDAO = new VehiculeDAO();
        StationnementDAO stationnementDAO = new StationnementDAO();

        
        // REER UNE PLACE
        
        System.out.println("\n--- Création d'une place ---");
        
        Place place = new Place();
        place.setNumero(18);  // Utiliser un numéro différent
        place.setType("Auto");
        place.setStatut("Occupee");
        place.setTarifHoraire(5.0);

        placeDAO.create(place);
        System.out.println("Place 18 créée avec tarif: " + place.getTarifHoraire() + " DH/heure");

        
        // CREER UN VEHICULE
        
        System.out.println("\n--- Création d'un véhicule ---");
        
        Vehicule v = new Vehicule();
        v.setMatricule("AA-124-BD");
        v.setMarque("Peugeot");
        v.setCategorie("Auto");

        vehiculeDAO.create(v);
        System.out.println("Véhicule créé");

       
        // ENTREE VEHICULE (sur la place 10)
        
        System.out.println("\n--- Entrée du véhicule ---");
        
        Stationnement s = new Stationnement();
        s.setNumero(10);  // Maintenant la place 10 existe
        s.setMatricule("AA-124-BD");
        s.setDateEntree(new Date());

        boolean entreeOk = stationnementDAO.create(s);
        
        if (entreeOk) {
            System.out.println("✓ Entrée enregistrée avec succès");
        } else {
            System.out.println("✗ Échec de l'entrée");
        }

        
       

        
        //  SORTIE VEHICULE
        
        System.out.println("\n--- Sortie du véhicule ---");
        
        // Récupérer le dernier stationnement (celui qui vient d'être créé)
        List<Stationnement> tous = stationnementDAO.findAll();
        
        if (!tous.isEmpty()) {
            Stationnement dernier = tous.get(tous.size() - 1);
            System.out.println("Traitement du stationnement ID: " + dernier.getId());
            
            Stationnement sortie = new Stationnement();
            sortie.setId(dernier.getId());
            
            stationnementDAO.update(sortie);
            System.out.println("✓ Sortie enregistrée");
        }

        
        // AFFICHER L'HISTORIQUE COMPLET
        
        System.out.println("\n===== HISTORIQUE COMPLET =====");
        
        List<Stationnement> historique = stationnementDAO.findAll();
        
        if (historique.isEmpty()) {
            System.out.println("Aucun stationnement dans l'historique");
        } else {
            for (Stationnement st : historique) {
                System.out.println("----------------------------------------");
                System.out.println("ID: " + st.getId());
                System.out.println("Place: " + st.getNumero());
                System.out.println("Véhicule: " + st.getMatricule());
                System.out.println("Date entrée: " + st.getDateEntree());
                System.out.println("Date sortie: " + st.getDateSortie());
                System.out.println("MONTANT: " + st.getMontant() + " DH");
            }
        }

        
        // AFFICHER L'ÉTAT DES PLACES
        
        System.out.println("\n===== ÉTAT DES PLACES =====");
        
        List<Place> places = placeDAO.findAll();
        for (Place p : places) {
            System.out.println("Place " + p.getNumero() + 
                             " | Type: " + p.getType() + 
                             " | Statut: " + p.getStatut() + 
                             " | Tarif: " + p.getTarifHoraire() + " DH");
        }

        System.out.println("\n===== FIN TEST =====");
    }
}