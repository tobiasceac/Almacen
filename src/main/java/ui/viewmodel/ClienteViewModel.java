/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.viewmodel;

import data.dao.ClienteDAO;
import data.jasper.JasperBase;
import data.model.Cliente;
import excepciones.ClienteAlreadyExistsException;
import excepciones.ClienteNotFoundException;
import excepciones.DataAccessException;
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
   private final JasperBase jCliente;
   
   private final String rutaPorCodigo ="src/main/java/resources/jasper/ListadoPorCodigoClientes.jasper";
   private final String rutaPrintPorCodigo ="src/main/java/resources/PDF/Listados Por Codigos Clientes.pdf";

   private final String rutaEntreCodigo ="src/main/java/resources/jasper/EntreCodigosClientes.jasper";
   private final String rutaPrintEntreCodigo ="src/main/java/resources/PDF/Entre Codigos Clientes.pdf";

   private final String rutaGrafico ="src/main/java/resources/jasper/GraficoCliente.jasper";
   private final String rutaPrintGrafico ="src/main/java/resources/PDF/Grafico Cliente.pdf";
   
   
   public ClienteViewModel(){
        this.clienteDAO = new ClienteDAO();
        this.jCliente = new JasperBase();
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
            ClienteAlreadyExistsException, DataAccessException {
       
       Cliente cliente = new Cliente(codigo, nif, apellidos, nombre, domicilio, codigoPostal, localidad, telefono, movil, fax, email, total);
       clienteDAO.insertar(cliente);
   }
   
    public Cliente consultaPorCodigo(String codigo)throws ClienteNotFoundException, DataAccessException {
       return clienteDAO.buscarPorCodigo(codigo);
   }
   
   public void bajaCliente(String codigo) throws DataAccessException, ClienteNotFoundException {
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
            ClienteNotFoundException, DataAccessException{
       
       Cliente cliente = new Cliente(codigo, nif, apellidos, nombre, domicilio, codigoPostal, localidad, telefono, movil, fax, email);
       clienteDAO.actualizar(cliente);
   }
   
   public void jasperClientePorCodigo() throws JRException, SQLException, DataAccessException{
       jCliente.jListadoPorCodigo(rutaPorCodigo, rutaPrintPorCodigo);
   }
   
   public void jasperClienteEntreCodigos(String codigoUno, String codigoDos) throws JRException, SQLException, DataAccessException{
       Map<String, Object> parametros = new HashMap<>();
       
       int cod1 = Integer.parseInt(codigoUno);
       int cod2 = Integer.parseInt(codigoDos);
       
       parametros.put("codigo", cod1);
       parametros.put("codigo2", cod2);

       
       jCliente.jEntreCodigos(parametros, rutaEntreCodigo, rutaPrintEntreCodigo);
   }
   
   public void jasperClienteGrafico() throws JRException, SQLException, DataAccessException{
       jCliente.jGrafico(rutaGrafico, rutaPrintGrafico);
   }
}
