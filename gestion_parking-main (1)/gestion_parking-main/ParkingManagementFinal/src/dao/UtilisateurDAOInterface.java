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


import entites.Utilisateur;

public interface UtilisateurDAOInterface {
    boolean ajouterUtilisateur(Utilisateur utilisateur, String motDePasseClair);
    Utilisateur authentifier(String nomUtilisateur, String motDePasseClair);
    boolean modifierMotDePasse(int id, String ancienMotDePasse, String nouveauMotDePasse);
    boolean supprimerUtilisateur(int id);
    boolean utilisateurExiste(String nomUtilisateur);
}
