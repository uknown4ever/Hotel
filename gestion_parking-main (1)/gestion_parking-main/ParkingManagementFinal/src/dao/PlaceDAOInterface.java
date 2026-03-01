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
import java.util.List;

public interface PlaceDAOInterface {

    boolean create(Place p);

    boolean update(Place p);

    void delete(int numero);

    Place findByNumero(int numero);

    List<Place> findAll();

}