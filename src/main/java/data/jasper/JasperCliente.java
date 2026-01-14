/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.jasper;

import data.database.ConexionDB;
import java.sql.SQLException;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author tobias
 */
public class JasperCliente {
    private ConexionDB conn;

    public JasperCliente() {
        this.conn = new ConexionDB();
    }
    
    private void jasperMethods(String ruta)throws JRException, SQLException{
        JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(ruta);
        JasperPrint print = JasperFillManager.fillReport(report, null, conn.connectDataBase());
        JasperExportManager.exportReportToPdfFile(print, "/Users/tobias/NetBeansProjects/Almacen/src/main/java/resources/PDF/Listados Por Codigos.pdf");
    }

    public void jListadoPorCodigo() throws JRException, SQLException{
        String rutaJasper = "/Users/tobias/NetBeansProjects/Almacen/src/main/java/resources/jasper/ListadoPorCodigoClientes.jasper";
        jasperMethods(rutaJasper);
    }
    
    public void jEntreCodigos(Map<String, Object> parametros) throws JRException, SQLException{
        String rutaJasper = "/Users/tobias/NetBeansProjects/Almacen/src/main/java/resources/jasper/EntreCodigos.jasper";
        JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(rutaJasper);
        JasperPrint print = JasperFillManager.fillReport(report, parametros, conn.connectDataBase());
        JasperExportManager.exportReportToPdfFile(print, "/Users/tobias/NetBeansProjects/Almacen/src/main/java/resources/PDF/Entre Codigos.pdf");
    }   
    
    public void jGrafico() throws JRException, SQLException{
       //TODO
    }  
    
    
}
