/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

/**
 *
 * @author PC
 */


public class Vehicule {

    
    private String matricule;
    private String marque;
    private String categorie;

    public Vehicule() {}

    public Vehicule(String matricule, String marque, String categorie) {

        this.matricule = matricule;
        this.marque = marque;
        this.categorie = categorie;

    }

   

    public String getMatricule() {
        return matricule; }

    public void setMatricule(String matricule) { 
        this.matricule = matricule; }

    public String getMarque() {
        return marque; }

    public void setMarque(String marque) { 
        this.marque = marque; }

    public String getCategorie() {
        return categorie; }

    public void setCategorie(String categorie) {
        this.categorie = categorie; }

}

