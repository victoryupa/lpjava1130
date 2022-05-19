/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.matricula.logic;

import com.edu.sise.matricula.dao.DAOManager;
import com.edu.sise.matricula.dao.TutorDao;
import com.edu.sise.matricula.entity.Tutor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
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
public class TutorLogic {
    
    List<Tutor> lista;
    TutorDao dao = DAOManager.getInstancia().getTutorDao();
    
    public TutorLogic(){
        lista = new ArrayList<>();
    }
        
    public DefaultTableModel getModelo(){
         DefaultTableModel modelo = new DefaultTableModel();
         
         //crear las columnas
         modelo.addColumn("ID");
         modelo.addColumn("DNI");
         modelo.addColumn("NOMBRE");
         modelo.addColumn("PAPELLIDO");
         modelo.addColumn("SAPELLIDO");
         modelo.addColumn("FNACIMIENTO");
         modelo.addColumn("TELEFONO");
         modelo.addColumn("IDPROV");
         
//         cargarData(); //obtener desde base de datos
        lista = dao.obtenerTodos();
         
         //llenar las filas
         
         for(Tutor obj : lista){
             Object data[] ={
                 obj.getId_tutor(),
                 obj.getDni(),
                 obj.getNombre(),
                 obj.getPapellido(),
                 obj.getSapellido(),
                 obj.getFnacimiento(),
                 obj.getTelefono(),
                 obj.getId_prov()
             };
             
             modelo.addRow(data);
         }
         
         return modelo;
         
    }
    
    public void imprimir(JTable tabla){
        tabla.setModel(getModelo());
    }
    
    public boolean agregarTutor(Tutor o){
        return dao.agregarTutor(o);
    }
    
    public boolean modificarTutor(Tutor o){
        return dao.modificarTutor(o);
    }
    
    public boolean eliminarTutor(Integer id){
        return dao.eliminarTutor(id);
    }
    
    public void llenarCboTutores(JComboBox cbo){
        DefaultComboBoxModel  modelo = new DefaultComboBoxModel();
        lista = dao.obtenerTodos();
        for(Tutor obj : lista){
            modelo.addElement(obj);
        }
        
        cbo.setModel(modelo);
    }
    
    public void seleccionarCboTutores(JComboBox cbo, Integer id){
        DefaultComboBoxModel modelo = (DefaultComboBoxModel)cbo.getModel();
        
        for(int i=0; i<modelo.getSize();i++){
            if(((Tutor)modelo.getElementAt(i)).getId_tutor()==id)
                modelo.setSelectedItem((Tutor)modelo.getElementAt(i));
        }
    }
    
   public int cargaMasiva(String rutaArchivo){
       int registros_afectados = 0;
       List<Tutor> listaCargaMasiva = new ArrayList<>();
       try {
           FileInputStream archivo = new FileInputStream(rutaArchivo);
           DataInputStream entrada = new DataInputStream(archivo);
           BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
           String linea;
           while((linea=buffer.readLine())!=null){
               String[] campos = linea.split("\\|");
               listaCargaMasiva.add(
                       new Tutor(
                        -1,
                        campos[0], //dni,
                        campos[1], //nombre
                        campos[2], //papellido
                        campos[3], //sapellido
                        LocalDate.parse(campos[4]), //fnacimiento
                        campos[5], //telefono
                        Integer.parseInt(campos[6]) //id_prov
               ));
           }
           entrada.close();
       } catch (Exception e) {
           System.out.println("Error Carga Masiva: " + e.getMessage());
       }
       int[] rpta = dao.cargaMasiva(listaCargaMasiva);
       for(int i=0; i<rpta.length;i++)
           registros_afectados += rpta[i];
       
       return registros_afectados;       
   }
}
