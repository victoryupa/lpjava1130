/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.matricula.logic;

import com.edu.sise.matricula.dao.CarreraDao;
import com.edu.sise.matricula.dao.DAOManager;
import com.edu.sise.matricula.entity.Carrera;
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
public class CarreraLogic {
    
    List<Carrera> lista;
    CarreraDao dao = DAOManager.getInstancia().getCarreraDao();
    
    public CarreraLogic(){
        lista = new ArrayList<>();
    }
        
    public DefaultTableModel getModelo(){
         DefaultTableModel modelo = new DefaultTableModel();
         
         //crear las columnas
         modelo.addColumn("ID");
         modelo.addColumn("NOMBRE");
         
//         cargarData(); //obtener desde base de datos
        lista = dao.obtenerTodos();
         
         //llenar las filas
         
         for(Carrera obj : lista){
             Object data[] ={
                 obj.getId_carrera(),
                 obj.getNombre()
             };
             
             modelo.addRow(data);
         }
         
         return modelo;
         
    }
    
    public void imprimir(JTable tabla){
        tabla.setModel(getModelo());
    }
    
    public boolean agregarCarrera(Carrera o){
        return dao.agregarCarrera(o);
    }
    
    public boolean modificarCarrera(Carrera o){
        return dao.modificarCarrera(o);
    }
    
    public boolean eliminarCarrera(Integer id){
        return dao.eliminarCarrera(id);
    }
    
    public void llenarCboCarreras(JComboBox cbo){
        DefaultComboBoxModel  modelo = new DefaultComboBoxModel();
        lista = dao.obtenerTodos();
        for(Carrera obj : lista){
            modelo.addElement(obj);
        }
        
        cbo.setModel(modelo);
    }
    
    public void seleccionarCboCarreras(JComboBox cbo, Integer id){
        DefaultComboBoxModel modelo = (DefaultComboBoxModel)cbo.getModel();
        
        for(int i=0; i<modelo.getSize();i++){
            if(((Carrera)modelo.getElementAt(i)).getId_carrera()==id)
                modelo.setSelectedItem((Carrera)modelo.getElementAt(i));
        }
    }
}
