/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.jasper;

import data.database.ConexionDB;
import excepciones.DataAccessException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author tobias
 */
public class JasperBase {
    private final ConexionDB conn;

    public JasperBase() {
        this.conn = new ConexionDB();
    }
    
    private void jasperMethods(String ruta, String rutaPrint)throws JRException, SQLException, DataAccessException{
        JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(ruta);
        JasperPrint print = JasperFillManager.fillReport(report, null, conn.connectDataBase());
        JasperExportManager.exportReportToPdfFile(print, rutaPrint);
            File pdfFile = new File(rutaPrint);
        if (pdfFile.exists() && Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException ex) {
                System.getLogger(JasperBase.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }
    
    public void jListadoPorCodigo(String rutaJasper, String rutaPrint) throws JRException, SQLException, DataAccessException{
        jasperMethods(rutaJasper, rutaPrint);
    }
    
    public void jEntreCodigos(Map<String, Object> parametros, String rutaJasper, String rutaPrint) throws JRException, SQLException, DataAccessException{
        JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(rutaJasper);
        JasperPrint print = JasperFillManager.fillReport(report, parametros, conn.connectDataBase());
        JasperExportManager.exportReportToPdfFile(print, rutaPrint);
        File pdfFile = new File(rutaPrint);
        if (pdfFile.exists() && Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException ex) {
                System.getLogger(JasperBase.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }   
    
    public void jGrafico(String rutaJasper, String rutaPrint) throws JRException, SQLException, DataAccessException{
        jasperMethods(rutaJasper, rutaPrint);
    } 
    
    public void jFactura(Map<String, Object> parametros, String rutaJasper, String rutaPrint) throws JRException, SQLException, DataAccessException{
        JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(rutaJasper);
        JasperPrint print = JasperFillManager.fillReport(report, parametros, conn.connectDataBase());
        JasperExportManager.exportReportToPdfFile(print, rutaPrint);
        File pdfFile = new File(rutaPrint);
        if (pdfFile.exists() && Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException ex) {
                System.getLogger(JasperBase.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }  
    
}
