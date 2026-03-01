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


import entites.Stationnement;
import java.util.List;

public interface StationnementDAOInterface {

    boolean create(Stationnement s);

    void update(Stationnement s);

    void delete(int id);

    Stationnement findById(int id);

    List<Stationnement> findAll();

}
