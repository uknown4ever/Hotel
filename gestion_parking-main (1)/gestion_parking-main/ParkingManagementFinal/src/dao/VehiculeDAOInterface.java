/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;
/**
 
 * @author PC
 */


import entites.Vehicule;
import java.util.List;

public interface VehiculeDAOInterface {

    boolean create(Vehicule v);

    boolean update(Vehicule v);

    void delete(String matricule);

    Vehicule findByMatricule(String matricule);

    List<Vehicule> findAll();

}
