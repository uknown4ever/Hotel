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




import entites.Vehicule;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculeDAO implements VehiculeDAOInterface {

    Connection conn = DBConnection.getConnection();

    @Override
    public boolean create(Vehicule v){

        try{

            String sql="INSERT INTO vehicule VALUES(?,?,?)";

            PreparedStatement ps=conn.prepareStatement(sql);

            ps.setString(1,v.getMatricule());
            ps.setString(2,v.getMarque());
            ps.setString(3,v.getCategorie());

            int resultat = ps.executeUpdate();
            return resultat > 0; // renvoie true si insere
        }
        catch(Exception e){
            e.printStackTrace();
            return false;  // renvoie false e ncas d'erreur
        }

    }



    @Override
    public boolean update(Vehicule v){

        try{

            String sql="UPDATE vehicule SET marque=?,categorie=? WHERE matricule=?";

            PreparedStatement ps=conn.prepareStatement(sql);

            ps.setString(1,v.getMarque());
            ps.setString(2,v.getCategorie());
            ps.setString(3,v.getMatricule());

            int resultat = ps.executeUpdate();
            return resultat > 0;

        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }



    @Override
    public void delete(String matricule){

        try{

            String sql="DELETE FROM vehicule WHERE matricule=?";

            PreparedStatement ps=conn.prepareStatement(sql);

            ps.setString(1,matricule);

            ps.executeUpdate();

        }
        catch(Exception e){e.printStackTrace();}

    }



    @Override
    public Vehicule findByMatricule(String matricule){

        Vehicule v=null;

        try{

            String sql="SELECT * FROM vehicule WHERE matricule=?";

            PreparedStatement ps=conn.prepareStatement(sql);

            ps.setString(1,matricule);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){

                v=new Vehicule();

                v.setMatricule(rs.getString("matricule"));
                v.setMarque(rs.getString("marque"));
                v.setCategorie(rs.getString("categorie"));

            }

        }
        catch(Exception e){e.printStackTrace();}

        return v;

    }



    @Override
    public List<Vehicule> findAll(){

        List<Vehicule> list=new ArrayList<>();

        try{

            Statement st=conn.createStatement();

            ResultSet rs=st.executeQuery("SELECT * FROM vehicule");

            while(rs.next()){

                Vehicule v=new Vehicule();

                v.setMatricule(rs.getString("matricule"));
                v.setMarque(rs.getString("marque"));
                v.setCategorie(rs.getString("categorie"));

                list.add(v);

            }

        }
        catch(Exception e){
            e.printStackTrace();}

        return list;

    }

}