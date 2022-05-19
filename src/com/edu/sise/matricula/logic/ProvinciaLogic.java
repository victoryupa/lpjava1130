/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.matricula.logic;

import com.edu.sise.matricula.dao.DAOManager;
import com.edu.sise.matricula.dao.ProvinciaDao;
import com.edu.sise.matricula.entity.Provincia;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Carlos
 */
public class ProvinciaLogic {
    
    List<Provincia> lista;
    ProvinciaDao dao = DAOManager.getInstancia().getProvinciaDao();
    
    public ProvinciaLogic(){
        lista = new ArrayList<>();
    }
    
//    public void cargarData(){
//        lista.add(new Provincia(100,"LIMA"));
//        lista.add(new Provincia(200,"LA LIBERTAD"));
//        lista.add(new Provincia(300,"TUMBES"));
//        lista.add(new Provincia(400,"PIURA"));
//    }
    
    public DefaultTableModel getModelo(){
         DefaultTableModel modelo = new DefaultTableModel();
         
         //crear las columnas
         modelo.addColumn("ID");
         modelo.addColumn("NOMBRE");
         modelo.addColumn("ID_DEPA");
         modelo.addColumn("DES_ID_DEPA");
         
//         cargarData(); //obtener desde base de datos
        //lista  = new ProvinciaDao().obtenerTodos();
        lista = dao.obtenerTodos();
         
         //llenar las filas
         
         for(Provincia obj : lista){
             Object data[] ={
                 obj.getId_prov(),
                 obj.getNombre(),
                 obj.getId_depa(),
                 obj.getDes_id_depa()
             };
             
             modelo.addRow(data);
         }
         
         return modelo;
         
    }
    
    public DefaultTableModel getModelo(String nombre){
         DefaultTableModel modelo = new DefaultTableModel();
         
         //crear las columnas
         modelo.addColumn("ID");
         modelo.addColumn("NOMBRE");
         modelo.addColumn("ID_DEPA");
         modelo.addColumn("DES_ID_DEPA");
         
//         cargarData(); //obtener desde base de datos
        lista = dao.buscar(nombre);
         
         //llenar las filas
         
         for(Provincia obj : lista){
             Object data[] ={
                 obj.getId_prov(),
                 obj.getNombre(),
                 obj.getId_depa(),
                 obj.getDes_id_depa()
             };
             
             modelo.addRow(data);
         }
         
         return modelo;
         
    }
    
    public void imprimir(JTable tabla){
        tabla.setModel(getModelo());
    }
    
    public void imprimir(JTable tabla, String nombre){
        tabla.setModel(getModelo(nombre));
    }
    
    public boolean agregarProvincia(Provincia o){
        return dao.agregarProvincia(o);
    }
    
    public boolean modificarProvincia(Provincia o){
        return dao.modificarProvincia(o);
    }
    
    public boolean eliminarProvincia(Integer id){
        return dao.eliminarProvincia(id);
    }
    
    public void generarReporte(){
        JasperReport reporte;
        
        //Necesitamos el jasper
        String ruta = "D:\\reportes_1130\\rpt_provincias_1130.jasper";
        try {
            reporte =  (JasperReport)JRLoader.loadObjectFromFile(ruta);  
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("empresa", "SISE SAC");
            parametros.put("ruc", "20202020201");
            
            JasperPrint jprint = JasperFillManager.fillReport(reporte,parametros, 
                    new JRBeanCollectionDataSource(dao.obtenerTodos()));
            JasperViewer jViewer = new JasperViewer(jprint,false);
            jViewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jViewer.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error JR: " + e.getMessage());
        }
        
    }
    
    public void llenarCboProvincias(JComboBox cbo){
        DefaultComboBoxModel  modelo = new DefaultComboBoxModel();
        lista = dao.obtenerTodos();
        for(Provincia obj : lista){
            modelo.addElement(obj);
        }
        
        cbo.setModel(modelo);
    }
    
    public void seleccionarCboProvincias(JComboBox cbo, Integer id){
        DefaultComboBoxModel modelo = (DefaultComboBoxModel)cbo.getModel();
        
        for(int i=0; i<modelo.getSize();i++){
            if(((Provincia)modelo.getElementAt(i)).getId_prov()==id)
                modelo.setSelectedItem((Provincia)modelo.getElementAt(i));
        }
    }
}
