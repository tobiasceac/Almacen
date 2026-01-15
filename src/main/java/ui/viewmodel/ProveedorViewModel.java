/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.viewmodel;

import data.dao.ProveedorDAO;
import data.jasper.JasperProveedor;
import data.model.Proveedor;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author tobias
 */
public class ProveedorViewModel {
    
   private ProveedorDAO proveedorDAO;
   private JasperProveedor jProveedor;
   
   public ProveedorViewModel(){
        this.proveedorDAO = new ProveedorDAO();
   }
   
   public void altaProveedor (
           String codigo, 
           String nif, 
           String apellidos, 
           String nombre, 
           String domicilio, 
           String codigoPostal, 
           String localidad, 
           String telefono, 
           String movil, 
           String fax, 
           String email, 
           float total
   ) throws 
            SQLException {
       
       Proveedor proveedor = new Proveedor(codigo, nif, apellidos, nombre, domicilio, codigoPostal, localidad, telefono, movil, fax, email, total);
       proveedorDAO.insertar(proveedor);
   }
   
    public Proveedor consultaPorCodigo(String codigo)throws SQLException {
       return proveedorDAO.buscarPorCodigo(codigo);
   }
   
   public void bajaProveedor(String codigo) throws SQLException {
       proveedorDAO.borrar(codigo);
   }
   
   public void modificarProveedor(
           String codigo,
           String nif, 
           String apellidos, 
           String nombre, 
           String domicilio, 
           String codigoPostal, 
           String localidad, 
           String telefono, 
           String movil, 
           String fax, 
           String email
   ) throws 
            SQLException{
       
       Proveedor proveedor = new Proveedor(codigo, nif, apellidos, nombre, domicilio, codigoPostal, localidad, telefono, movil, fax, email);
       proveedorDAO.actualizar(proveedor);
   }
   
   public Proveedor consultaEntreCodigos(String codigoX, String codigoZ) throws SQLException {
       return proveedorDAO.buscarEntreCodigos(codigoX, codigoZ);
    }
   
      public void jasperProveedorPorCodigo() throws JRException, SQLException{
       jProveedor.jListadoPorCodigo();
   }
   
   public void jasperProveedorEntreCodigos(String codigoUno, String codigoDos) throws JRException, SQLException{
       Map<String, Object> parametros = new HashMap<>();
       
       int cod1 = Integer.parseInt(codigoUno);
       int cod2 = Integer.parseInt(codigoDos);
       
       parametros.put("codigo", cod1);
       parametros.put("codigo2", cod2);

       
       jProveedor.jEntreCodigos(parametros);
   }
   
}