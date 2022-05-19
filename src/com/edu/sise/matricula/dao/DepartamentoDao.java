/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.matricula.dao;

import com.edu.sise.matricula.entity.Departamento;
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
public class DepartamentoDao {
    
    //atributos
    private Connection cn = null;
    private Statement st = null; //nos permite ejecutar sentencias SQL contra BD
    private ResultSet rs = null; //nos permite tener una tabla virtual
    
    //constructor
    //constructor por defecto o sin parametros
    public DepartamentoDao(){
        //permite establecer la conexion a BD por medio de mi clase Conexion.java
        //sucede cuando creemos un nuevo objeto de la clase DepartamentoDao
        cn = new Conexion().getCn();
    }
    
    public List<Departamento> obtenerTodos(){
        
        //crear el sentencia SQL que quiero ejecutar
        String sql ="select * from departamentos";
        List<Departamento> lista = new ArrayList<>();
        
        try {
            //código para ejecutar la sentencia con la bd
            
            //establezco la conexión con el state....
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            //llenar la lista
            while(rs.next()){  //condición para determinar si existe un siguiente dato
                lista.add(new Departamento(rs.getInt("id_depa"), rs.getString("nombre")));
            }
        } catch (SQLException ex) {
            System.out.println("DepartamentoDao: " + ex.getMessage());
        }
        return lista;
    }
    //tipo de dato boolean tiene dos valor posibles: true o false
    public boolean agregarDepartamento(Departamento o){
        String sql = "insert into departamentos(nombre) values('"+o.getNombre()+"')";
        //representar la cantidad de registros afectados
        int c =-1;
        
        try {
            st = cn.createStatement();
            c = st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("DepartamentoDao: " + e.getMessage());
        }
        
        return (c>0);
    }
    
    //tipo de dato boolean tiene dos valor posibles: true o false
    public boolean modificarDepartamento(Departamento o){
        String sql = "update departamentos set nombre = '"+o.getNombre()+"' where id_depa = "+o.getId_depa();
        //representar la cantidad de registros afectados
        int c =-1;
        
        try {
            st = cn.createStatement();
            c = st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("DepartamentoDao: " + e.getMessage());
        }
        
        return (c>0);
    }
    
    //tipo de dato boolean tiene dos valor posibles: true o false
    public boolean eliminarDepartamento(Integer id){
        String sql = "delete from departamentos where id_depa = "+id;
        //representar la cantidad de registros afectados
        int c =-1;
        
        try {
            st = cn.createStatement();
            c = st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("DepartamentoDao: " + e.getMessage());
        }
        
        return (c>0);
    }
    
    
}
