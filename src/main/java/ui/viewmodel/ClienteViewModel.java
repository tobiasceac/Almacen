/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.viewmodel;

import data.dao.ClienteDAO;
import data.jasper.JasperCliente;
import data.model.Cliente;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author tobias
 */
public class ClienteViewModel {
  
   private final ClienteDAO clienteDAO;
   private final JasperCliente jCliente;
   
   public ClienteViewModel(){
        this.clienteDAO = new ClienteDAO();
        this.jCliente = new JasperCliente();
   }
   
   public void altaCLiente (
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
       
       Cliente cliente = new Cliente(codigo, nif, apellidos, nombre, domicilio, codigoPostal, localidad, telefono, movil, fax, email, total);
       clienteDAO.insertar(cliente);
   }
   
    public Cliente consultaPorCodigo(String codigo)throws SQLException {
       return clienteDAO.buscarPorCodigo(codigo);
   }
   
   public void bajaCliente(String codigo) throws SQLException {
       clienteDAO.borrar(codigo);
   }
   
   public void modificarCliente(
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
       
       Cliente cliente = new Cliente(codigo, nif, apellidos, nombre, domicilio, codigoPostal, localidad, telefono, movil, fax, email);
       clienteDAO.actualizar(cliente);
   }
   
   public Cliente consultaEntreCodigos(String codigoX, String codigoZ) throws SQLException {
       return clienteDAO.buscarEntreCodigos(codigoX, codigoZ);
    }
   
   public void jasperClientePorCodigo() throws JRException, SQLException{
       jCliente.jListadoPorCodigo();
   }
   
   public void jasperClienteEntreCodigos(String codigoUno, String codigoDos) throws JRException, SQLException{
       Map<String, Object> parametros = new HashMap<>();
       
       int cod1 = Integer.parseInt(codigoUno);
       int cod2 = Integer.parseInt(codigoDos);
       
       parametros.put("codigo", cod1);
       parametros.put("codigo2", cod2);

       
       jCliente.jEntreCodigos(parametros);
   }
   
}
