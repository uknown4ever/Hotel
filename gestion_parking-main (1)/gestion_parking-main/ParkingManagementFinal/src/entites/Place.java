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


public class Place {

    
    private int numero;
    private String type;
    private String statut;
    private double tarifHoraire;

    public Place() {}

    public Place(int numero, String type, String statut, double tarifHoraire) {

        this.numero = numero;
        this.type = type;
        this.statut = statut;
        this.tarifHoraire = tarifHoraire;

    }



    public int getNumero() {
        return numero; }

    public void setNumero(int numero) {
        this.numero = numero; }

    public String getType() {
        return type; }

    public void setType(String type) {
        this.type = type; }

    public String getStatut() {
        return statut; }

    public void setStatut(String statut) {
        this.statut = statut; }

    public double getTarifHoraire() {
        return tarifHoraire; }

    public void setTarifHoraire(double tarifHoraire) {
        this.tarifHoraire = tarifHoraire; }

    public Object ggetNumero() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

