/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Utilisateur {
    private int id;
    private String nomUtilisateur;
    private String motDePasse;
    private String role;
    private String email;  // AJOUTER CET ATTRIBUT
    private String dateCreation;
    
    public Utilisateur() {}
    
    public Utilisateur(String nomUtilisateur, String motDePasse, String role) {
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.role = role;
    }
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNomUtilisateur() { return nomUtilisateur; }
    public void setNomUtilisateur(String nomUtilisateur) { this.nomUtilisateur = nomUtilisateur; }
    
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getEmail() { return email; }          // AJOUTER
    public void setEmail(String email) { this.email = email; }  // AJOUTER
    
    public String getDateCreation() { return dateCreation; }
    public void setDateCreation(String dateCreation) { this.dateCreation = dateCreation; }
}
