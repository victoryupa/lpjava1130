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
public class Departamento {
    public Integer id_depa;
    public String nombre;
    
    public Departamento(Integer id_depa, String nombre){
        this.id_depa = id_depa;
        this.nombre = nombre;
    }

    public Integer getId_depa() {
        return id_depa;
    }

    public void setId_depa(Integer id_depa) {
        this.id_depa = id_depa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id_depa + " - " + nombre ;
    }
    
}
