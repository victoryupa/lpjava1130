/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.matricula.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Carlos
 */
public class Conexion {
    
    //atributos para mi conexion con base de datos
    private static String url="jdbc:mysql://localhost:3306/lpjava1130";
    private static String user="root";
    private static String password="root";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    
    private Connection cn = null;
    
    static{
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public Connection getCn(){
        
        try {
            cn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return cn;
    }
}
