/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.matricula.dao;

import com.edu.sise.matricula.entity.Profesor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class ProfesorDao {
    
    //atributos
    private Connection cn = null;
    private Statement st = null; //nos permite ejecutar sentencias SQL contra BD
    private ResultSet rs = null; //nos permite tener una tabla virtual
    private PreparedStatement ps = null;
    private CallableStatement cs = null;
    
    //atributos para representar el SQL (INSERTAR, MODIFICAR,ELIMINAR, TODOS, FILTRO)
    final String INSERTAR ="{call pa_insertar_profesores(?,?,?,?,?,?,?,?)}";
    final String MODIFICAR ="{call pa_modificar_profesor(?,?,?,?,?,?,?,?,?)}";
    final String ELIMINAR ="{call pa_eliminar_profesor(?)}";
    final String TODOS ="{call pa_listar_profesores()}";
    
    //constructor
    //constructor por defecto o sin parametros
    public ProfesorDao(){
        //permite establecer la conexion a BD por medio de mi clase Conexion.java
        //sucede cuando creemos un nuevo objeto de la clase CarreraDao
        cn = new Conexion().getCn();
    }
    
    public List<Profesor> obtenerTodos(){
        
        //crear el sentencia SQL que quiero ejecutar
        //String sql ="{call pa_listar_profesores()}";
        List<Profesor> lista = new ArrayList<>();
        
        try {
            //código para ejecutar la sentencia con la bd
            
            //establezco la conexión con el state....
            //st = cn.createStatement();
            //ps = cn.prepareStatement(sql);
            cs = cn.prepareCall(TODOS);
            //rs = st.executeQuery(sql);
            //rs = ps.executeQuery();
            rs = cs.executeQuery();
            //llenar la lista
            while(rs.next()){  //condición para determinar si existe un siguiente dato
                lista.add(new Profesor(
                        rs.getInt("id_profe"), 
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("papellido"),
                        rs.getString("sapellido"),
                        rs.getDate("fnacimiento").toLocalDate(),
                        rs.getString("telefono"),
                        rs.getInt("id_carrera"),
                        rs.getInt("id_prov")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("ProfesorDao: " + ex.getMessage());
        }
        return lista;
    }
    //tipo de dato boolean tiene dos valor posibles: true o false
    public boolean agregarProfesor(Profesor o){
        //String sql = "insert into carreras(nombre) values('"+o.getNombre()+"')";
        //String sql = "{call pa_insertar_profesores(?,?,?,?,?,?,?)}";
        //representar la cantidad de registros afectados
        int c =-1;
        
        try {
            //st = cn.createStatement();
            //ps = cn.prepareStatement(sql);
            cs = cn.prepareCall(INSERTAR);
            //ps.setString(1, o.getNombre());
            int x =1;
            cs.setString(x++, o.getDni());
            cs.setString(x++, o.getNombre());
            cs.setString(x++, o.getPapellido());
            cs.setString(x++, o.getSapellido());
            cs.setDate(x++, Date.valueOf(o.getFnacimiento()));
            cs.setString(x++, o.getTelefono());
            cs.setInt(x++, o.getId_carrera());
            cs.setInt(x++, o.getId_prov());
            //c = st.executeUpdate(sql);
            //c = ps.executeUpdate();
            c = cs.executeUpdate();
        } catch (Exception e) {
            System.out.println("ProfesorDao: " + e.getMessage());
        }
        
        return (c>0);
    }
    
    //tipo de dato boolean tiene dos valor posibles: true o false
    public boolean modificarProfesor(Profesor o){
        //String sql = "update carreras set nombre = '"+o.getNombre()+"' where id_depa = "+o.getId_depa();
        //String sql = "{call pa_modificar_profesor(?,?,?,?,?,?,?,?)} ";
        //representar la cantidad de registros afectados
        int c =-1;
        
        try {
            //st = cn.createStatement();
            cs = cn.prepareCall(MODIFICAR);
            //ps.setString(1, o.getNombre());
            int x =1;
            cs.setString(x++, o.getDni());
            cs.setString(x++, o.getNombre());
            cs.setString(x++, o.getPapellido());
            cs.setString(x++, o.getSapellido());
            cs.setDate(x++, Date.valueOf(o.getFnacimiento()));
            cs.setString(x++, o.getTelefono());
            cs.setInt(x++, o.getId_carrera());
            cs.setInt(x++, o.getId_prov());
            cs.setInt(x++, o.getId_profe());
            //c = st.executeUpdate(sql);
            //c = ps.executeUpdate();
            c = cs.executeUpdate();
        } catch (Exception e) {
            System.out.println("ProfesorDao: " + e.getMessage());
        }
        
        return (c>0);
    }
    
    //tipo de dato boolean tiene dos valor posibles: true o false
    public boolean eliminarProfesor(Integer id){
       // String sql = "{call pa_eliminar_profesor(?)} ";
        //representar la cantidad de registros afectados
        int c =-1;
        
        try {
            //st = cn.createStatement();
            //ps = cn.prepareStatement(sql);
            //ps.setInt(1, id);
            cs = cn.prepareCall(ELIMINAR);
            cs.setInt(1, id);
            //c = st.executeUpdate(sql);
            //c = ps.executeUpdate();
            c = cs.executeUpdate();
        } catch (Exception e) {
            System.out.println("ProfesorDao: " + e.getMessage());
        }
        
        return (c>0);
    }
    
    public int[] cargaMasiva(List<Profesor> lista){
        int[] respuesta =  null;
        //String sql = "{call pa_insertar_profesores(?,?,?,?,?,?,?)}";
        try {
            cs = cn.prepareCall(INSERTAR);
            for(Profesor o : lista){
                int x =1;
                cs.setString(x++, o.getDni());
                cs.setString(x++, o.getNombre());
                cs.setString(x++, o.getPapellido());
                cs.setString(x++, o.getSapellido());
                cs.setDate(x++, Date.valueOf(o.getFnacimiento()));
                cs.setString(x++, o.getTelefono());
                cs.setInt(x++, o.getId_carrera());
                cs.setInt(x++, o.getId_prov());
                cs.addBatch();
            }
            
            respuesta = cs.executeBatch();
        } catch (Exception e) {
            System.out.println("Error ProfesorDAO: " + e.getMessage());
        }
        
        return respuesta;
    }
    
    
}
