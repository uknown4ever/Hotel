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
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceDAO implements PlaceDAOInterface {

    Connection conn = DBConnection.getConnection();

    public PlaceDAO(){
        // constructeur vie suffit
    }

    @Override
    public boolean create(Place p){

        try{

            String sql="INSERT INTO place VALUES(?,?,?,?)";

            PreparedStatement ps=conn.prepareStatement(sql);

            ps.setInt(1,p.getNumero());
            ps.setString(2,p.getType());
            ps.setString(3,p.getStatut());
            ps.setDouble(4,p.getTarifHoraire());

            // executeUpdate() renvoie le nbr de lignes inserees (1 si ok)
            int resultat = ps.executeUpdate();
            
            // Si resultat > 0, l'insertion a marche, on renvoie true
            return resultat > 0;
            
        }
        catch(Exception e){
            e.printStackTrace();
            
            // Si une erreur arrive (connexion perdue), on renvoie false
            return false;
        }
    }
    


    @Override
    public boolean update(Place p){

        try{

            String sql="UPDATE place SET type=?,statut=?,tarifHoraire=? WHERE numero=?";

            PreparedStatement ps=conn.prepareStatement(sql);

            ps.setString(1,p.getType());
            ps.setString(2,p.getStatut());
            ps.setDouble(3,p.getTarifHoraire());
            ps.setInt(4,p.getNumero());

            // excuteUpdate renvoie le nbr de lignes modifiees
            int resultat = ps.executeUpdate();
            
            // renvoie true si la mise a jour a fonctionnee
            return resultat > 0;

        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }


    @Override
    public void delete(int numero){

        try{

            String sql="DELETE FROM place WHERE numero=?";

            PreparedStatement ps=conn.prepareStatement(sql);

            ps.setInt(1,numero);

            ps.executeUpdate();

        }
        catch(Exception e){e.printStackTrace();}

    }


    @Override
    public Place findByNumero(int numero){

        Place p=null;

        try{

            String sql="SELECT * FROM place WHERE numero=?";

            PreparedStatement ps=conn.prepareStatement(sql);

            ps.setInt(1,numero);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){

                p=new Place();

                p.setNumero(rs.getInt("numero"));
                p.setType(rs.getString("type"));
                p.setStatut(rs.getString("statut"));
                p.setTarifHoraire(rs.getDouble("tarifHoraire"));

            }

        }
        catch(Exception e){e.printStackTrace();}

        return p;

    }



    @Override
    public List<Place> findAll(){

        List<Place> list=new ArrayList<>();

        try{

            Statement st=conn.createStatement();

            ResultSet rs=st.executeQuery("SELECT * FROM place");

            while(rs.next()){

                Place p=new Place();

                p.setNumero(rs.getInt("numero"));
                p.setType(rs.getString("type"));
                p.setStatut(rs.getString("statut"));
                p.setTarifHoraire(rs.getDouble("tarifHoraire"));

                list.add(p);

            }

        }
        catch(Exception e){e.printStackTrace();}

        return list;

    }

}