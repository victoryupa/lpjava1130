/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.matricula.entity;

/**
 *
 * @author Carlos
 */
public class Carrera {
    //atributos
    private Integer id_carrera;
    private String nombre;
    
    //constructor
    public Carrera(Integer id_carrera, String nombre) {
        this.id_carrera = id_carrera;
        this.nombre = nombre;
    }
    
    //set y get

    public Integer getId_carrera() {
        return id_carrera;
    }

    public void setId_carrera(Integer id_carrera) {
        this.id_carrera = id_carrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return id_carrera + " - " + nombre ;
    }
    
}
