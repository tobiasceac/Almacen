/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.viewmodel;

import data.dao.ProveedorDAO;
import data.jasper.JasperBase;
import data.model.Proveedor;
import excepciones.ClienteNotFoundException;
import excepciones.DataAccessException;
import excepciones.ProveedorAlreadyExistsException;
import excepciones.ProveedorNotFoundException;
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
   private final JasperBase jProveedor;
   
   private final String rutaPorCodigo ="src/main/java/resources/jasper/ListadoPorCodigoProveedores.jasper";
   private final String rutaPrintPorCodigo ="src/main/java/resources/PDF/Listados Por Codigos Proveedores.pdf";

   private final String rutaEntreCodigo ="src/main/java/resources/jasper/EntreCodigosProveedores.jasper";
   private final String rutaPrintEntreCodigo ="src/main/java/resources/PDF/Entre Codigos Proveedores.pdf";

   private final String rutaGrafico ="src/main/java/resources/jasper/GraficoProveedor.jasper";
   private final String rutaPrintGrafico ="src/main/java/resources/PDF/Grafico Proveedor.pdf";
   
   public ProveedorViewModel(){
        this.proveedorDAO = new ProveedorDAO();
        this.jProveedor = new JasperBase();
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
            ProveedorAlreadyExistsException, DataAccessException {
       
       Proveedor proveedor = new Proveedor(codigo, nif, apellidos, nombre, domicilio, codigoPostal, localidad, telefono, movil, fax, email, total);
       proveedorDAO.insertar(proveedor);
   }
   
    public Proveedor consultaPorCodigo(String codigo)throws DataAccessException, ProveedorNotFoundException {
       return proveedorDAO.buscarPorCodigo(codigo);
   }
   
   public void bajaProveedor(String codigo) throws DataAccessException, ProveedorNotFoundException {
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
            DataAccessException, ProveedorNotFoundException{
       
       Proveedor proveedor = new Proveedor(codigo, nif, apellidos, nombre, domicilio, codigoPostal, localidad, telefono, movil, fax, email);
       proveedorDAO.actualizar(proveedor);
   }
   
   public void jasperProveedorPorCodigo() throws JRException, SQLException, DataAccessException{
       jProveedor.jListadoPorCodigo(rutaPorCodigo, rutaPrintPorCodigo);
   }
   
   public void jasperProveedorEntreCodigos(String codigoUno, String codigoDos) throws JRException, SQLException, DataAccessException{
       Map<String, Object> parametros = new HashMap<>();
       
       int cod1 = Integer.parseInt(codigoUno);
       int cod2 = Integer.parseInt(codigoDos);
       
       parametros.put("codigo", cod1);
       parametros.put("codigo2", cod2);

       
       jProveedor.jEntreCodigos(parametros, rutaEntreCodigo, rutaPrintEntreCodigo);
   }
   
   public void jasperProveedorGrafico() throws JRException, SQLException, DataAccessException{
       jProveedor.jGrafico(rutaGrafico, rutaPrintGrafico);
   }
   
}