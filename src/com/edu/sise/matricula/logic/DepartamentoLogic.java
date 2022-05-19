/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.matricula.logic;

import com.edu.sise.matricula.dao.DAOManager;
import com.edu.sise.matricula.dao.DepartamentoDao;
import com.edu.sise.matricula.entity.Departamento;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class DepartamentoLogic {
    
    List<Departamento> lista;
    DepartamentoDao dao = DAOManager.getInstancia().getDepartamentoDao();
    
    public DepartamentoLogic(){
        lista = new ArrayList<>();
    }
    
//    public void cargarData(){
//        lista.add(new Departamento(100,"LIMA"));
//        lista.add(new Departamento(200,"LA LIBERTAD"));
//        lista.add(new Departamento(300,"TUMBES"));
//        lista.add(new Departamento(400,"PIURA"));
//    }
    
    public DefaultTableModel getModelo(){
         DefaultTableModel modelo = new DefaultTableModel();
         
         //crear las columnas
         modelo.addColumn("ID");
         modelo.addColumn("NOMBRE");
         
//         cargarData(); //obtener desde base de datos
        lista = dao.obtenerTodos();
         
         //llenar las filas
         
         for(Departamento obj : lista){
             Object data[] ={
                 obj.getId_depa(),
                 obj.getNombre()
             };
             
             modelo.addRow(data);
         }
         
         return modelo;
         
    }
    
    public void imprimir(JTable tabla){
        tabla.setModel(getModelo());
    }
    
    public boolean agregarDepartamento(Departamento o){
        return dao.agregarDepartamento(o);
    }
    
    public boolean modificarDepartamento(Departamento o){
        return dao.modificarDepartamento(o);
    }
    
    public boolean eliminarDepartamento(Integer id){
        return dao.eliminarDepartamento(id);
    }
    
    public void llenarCboDepartamentos(JComboBox cbo){
        DefaultComboBoxModel  modelo = new DefaultComboBoxModel();
        lista = dao.obtenerTodos();
        for(Departamento obj : lista){
            modelo.addElement(obj);
        }
        
        cbo.setModel(modelo);
    }
    
    public void seleccionarCboDepartamentos(JComboBox cbo, Integer id){
        DefaultComboBoxModel modelo = (DefaultComboBoxModel)cbo.getModel();
        
        for(int i=0; i<modelo.getSize();i++){
            if(((Departamento)modelo.getElementAt(i)).getId_depa()==id)
                modelo.setSelectedItem((Departamento)modelo.getElementAt(i));
        }
    }
}
