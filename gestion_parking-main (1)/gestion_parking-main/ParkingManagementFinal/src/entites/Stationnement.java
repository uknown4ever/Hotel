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
import java.util.Date;

public class Stationnement {

    private int id;
    private int numero; //numero de la place
    private String matricule; //matricule de la vehicule
    private Date dateEntree;
    private Date dateSortie;
    private double montant;

    public Stationnement() {}

    public Stationnement(int numero, String matricule, Date dateEntree,Date dateSortie ,double montant) {

        this.numero = numero;
        this.matricule = matricule;
        this.dateEntree = dateEntree;
        this.dateSortie= dateSortie;
        this.montant = montant;

    }

    public int getId() {
        return id; }

    public void setId(int id) {
        this.id = id; }

    public int getNumero() {
        return numero; }
    
     public void setNumero(int numero) {
        this.numero = numero; }

    public String getMatricule() {
        return matricule; }
    
     public void setMatricule( String matricule) {
        this.matricule = matricule; }

    public Date getDateEntree() {
        return dateEntree; }
    
    public void setDateEntree( Date DateEntree) {
        this.dateEntree = DateEntree; }

     
    public Date getDateSortie() {
        return dateSortie; }
    
    public void setDateSortie( Date DateSortie) {
        this.dateSortie = DateSortie; }


    public double getMontant() { 
        return montant; }

    

    public void setMontant(double montant) {
        this.montant = montant; }

}

