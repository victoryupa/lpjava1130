/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.matricula.logic;

import com.edu.sise.matricula.dao.DAOManager;
import com.edu.sise.matricula.dao.ProfesorDao;
import com.edu.sise.matricula.entity.Profesor;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author Carlos
 */
public class ProfesorLogic {
    
    List<Profesor> lista;
    ProfesorDao dao = DAOManager.getInstancia().getProfesorDao();
    
    public ProfesorLogic(){
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
         modelo.addColumn("IDCARRERA");
         modelo.addColumn("IDPROV");
         
//         cargarData(); //obtener desde base de datos
        lista = dao.obtenerTodos();
         
         //llenar las filas
         
         for(Profesor obj : lista){
             Object data[] ={
                 obj.getId_profe(),
                 obj.getDni(),
                 obj.getNombre(),
                 obj.getPapellido(),
                 obj.getSapellido(),
                 obj.getFnacimiento(),
                 obj.getTelefono(),
                 obj.getId_carrera(),
                 obj.getId_prov()
             };
             
             modelo.addRow(data);
         }
         
         return modelo;
         
    }
    
    public void imprimir(JTable tabla){
        tabla.setModel(getModelo());
    }
    
    public boolean agregarProfesor(Profesor o){
        return dao.agregarProfesor(o);
    }
    
    public boolean modificarProfesor(Profesor o){
        return dao.modificarProfesor(o);
    }
    
    public boolean eliminarProfesor(Integer id){
        return dao.eliminarProfesor(id);
    }
    
    public void llenarCboProfesores(JComboBox cbo){
        DefaultComboBoxModel  modelo = new DefaultComboBoxModel();
        lista = dao.obtenerTodos();
        for(Profesor obj : lista){
            modelo.addElement(obj);
        }
        
        cbo.setModel(modelo);
    }
    
    public void seleccionarCboProfesores(JComboBox cbo, Integer id){
        DefaultComboBoxModel modelo = (DefaultComboBoxModel)cbo.getModel();
        
        for(int i=0; i<modelo.getSize();i++){
            if(((Profesor)modelo.getElementAt(i)).getId_profe()==id)
                modelo.setSelectedItem((Profesor)modelo.getElementAt(i));
        }
    }
    
   public int cargaMasiva(String rutaArchivo){
       int registros_afectados = 0;
       List<Profesor> listaCargaMasiva = new ArrayList<>();
       try {
           FileInputStream archivo = new FileInputStream(rutaArchivo);
           DataInputStream entrada = new DataInputStream(archivo);
           BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
           String linea;
           while((linea=buffer.readLine())!=null){
               String[] campos = linea.split("\\|");
               listaCargaMasiva.add(
                       new Profesor(
                        -1,
                        campos[0], //dni,
                        campos[1], //nombre
                        campos[2], //papellido
                        campos[3], //sapellido
                        LocalDate.parse(campos[4]), //fnacimiento
                        campos[5], //telefono
                        Integer.parseInt(campos[6]), //id_carrera
                        Integer.parseInt(campos[7]) //id_prov
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
   public void generarQR(String valor, JLabel lbl){
        ByteArrayOutputStream qr = QRCode.from(valor).to(ImageType.PNG).stream();
        ImageIcon icono = new ImageIcon(qr.toByteArray());
        lbl.setIcon(icono);
   }
}
