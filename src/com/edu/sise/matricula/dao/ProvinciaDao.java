/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.matricula.dao;

import com.edu.sise.matricula.entity.Provincia;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class ProvinciaDao {
    
    //atributos
    private Connection cn = null;
    private Statement st = null; //nos permite ejecutar sentencias SQL contra BD
    private ResultSet rs = null; //nos permite tener una tabla virtual
    
    //constructor
    //constructor por defecto o sin parametros
    public ProvinciaDao(){
        //permite establecer la conexion a BD por medio de mi clase Conexion.java
        //sucede cuando creemos un nuevo objeto de la clase ProvinciaDao
        cn = new Conexion().getCn();
        System.out.println("Se creo una instancia de ProvinciaDao...");
    }
    
    public List<Provincia> obtenerTodos(){
        
        //crear el sentencia SQL que quiero ejecutar
        String sql ="select prov.*, depa.nombre as 'des_id_depa'\n" +
            "from provincias prov inner join departamentos depa "
                + " on(prov.id_depa=depa.id_depa);";
        List<Provincia> lista = new ArrayList<>();
        
        try {
            //código para ejecutar la sentencia con la bd
            
            //establezco la conexión con el state....
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            //llenar la lista
            while(rs.next()){  //condición para determinar si existe un siguiente dato
                lista.add(new Provincia(
                        rs.getInt("id_prov"), 
                        rs.getString("nombre"),
                        rs.getInt("id_depa"),
                        rs.getString("des_id_depa")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("ProvinciaDao: " + ex.getMessage());
        }
        return lista;
    }
    
    public List<Provincia> buscar(String nombre){
        
        //crear el sentencia SQL que quiero ejecutar
        String sql ="select prov.*, depa.nombre as 'des_id_depa'\n" +
            "from provincias prov inner join departamentos depa on(prov.id_depa=depa.id_depa)\n" +
            "where prov.nombre like '%"+nombre+"%';";
        List<Provincia> lista = new ArrayList<>();
        
        try {
            //código para ejecutar la sentencia con la bd
            
            //establezco la conexión con el state....
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            //llenar la lista
            while(rs.next()){  //condición para determinar si existe un siguiente dato
                lista.add(new Provincia(
                        rs.getInt("id_prov"), 
                        rs.getString("nombre"),
                        rs.getInt("id_depa"),
                        rs.getString("des_id_depa")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("ProvinciaDao: " + ex.getMessage());
        }
        return lista;
    }
    //tipo de dato boolean tiene dos valor posibles: true o false
    public boolean agregarProvincia(Provincia o){
        String sql = "insert into provincias(nombre, id_depa) values('"+o.getNombre()+"',"+o.getId_depa()+")";
        //representar la cantidad de registros afectados
        int c =-1;
        
        try {
            st = cn.createStatement();
            c = st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("ProvinciaDao: " + e.getMessage());
        }
        
        return (c>0);
    }
    
    //tipo de dato boolean tiene dos valor posibles: true o false
    public boolean modificarProvincia(Provincia o){
        String sql = "update provincias set nombre = '"+o.getNombre()+"', id_depa = "+o.getId_depa()+" where id_prov = "+o.getId_prov();
        //representar la cantidad de registros afectados
        int c =-1;
        
        try {
            st = cn.createStatement();
            c = st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("ProvinciaDao: " + e.getMessage());
        }
        
        return (c>0);
    }
    
    //tipo de dato boolean tiene dos valor posibles: true o false
    public boolean eliminarProvincia(Integer id){
        String sql = "delete from provincias where id_prov = "+id;
        //representar la cantidad de registros afectados
        int c =-1;
        
        try {
            st = cn.createStatement();
            c = st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("ProvinciaDao: " + e.getMessage());
        }
        
        return (c>0);
    }
    
    
}
